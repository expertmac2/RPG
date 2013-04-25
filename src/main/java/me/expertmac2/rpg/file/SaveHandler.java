package me.expertmac2.rpg.file;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import me.expertmac2.rpg.Game;
import me.expertmac2.rpg.actors.Actor;
import me.expertmac2.rpg.logic.Task;
import me.expertmac2.rpg.maps.GameMap;
import me.expertmac2.rpg.maps.MapManager;
import me.expertmac2.rpg.util.RPGException;
import me.expertmac2.rpg.util.URLObjectInputStream;

import de.schlichtherle.truezip.file.TFile;
import de.schlichtherle.truezip.file.TFileInputStream;
import de.schlichtherle.truezip.file.TFileOutputStream;
import de.schlichtherle.truezip.file.TFileReader;

public class SaveHandler {

	private static SAXReader saxReader = new SAXReader();

	@SuppressWarnings("unchecked")
	public static void loadSave(Game game, String fileName) throws RPGException, 
	ClassNotFoundException, IOException, DocumentException {
		File saveInfoFile = new TFile("res/save/" + fileName + "/SaveInfo.xml");
		File loadedMaps = new TFile("res/save/" + fileName + "/LoadedMaps.ser");
		File scheduledTasks = new TFile("res/save/" + fileName + "/ScheduledTasks.ser");
		if (!saveInfoFile.exists() || !loadedMaps.exists() || !scheduledTasks.exists()) {
			throw new RPGException("Invalid/Corrupt save file. (" + !saveInfoFile.exists() + 
					", " + !loadedMaps.exists() + ", " + !scheduledTasks.exists() + ")");
		}
		Element saveInfo = saxReader.read(new FileReader(saveInfoFile)).getRootElement();
		URLObjectInputStream lmInput = new URLObjectInputStream(new FileInputStream(loadedMaps), game);
		URLObjectInputStream tasks = new URLObjectInputStream(new FileInputStream(scheduledTasks), game);
		try {
			if (!saveInfo.attributeValue("game").equals(game.gameName)) {
				throw new RPGException("Save file " + fileName + " is incompatible with the loaded game " + game.gameName + ".");
			}
			game.getMapManager().setLoadedMaps((DualHashBidiMap) lmInput.readObject());
			game.getScheduler().setHashMap((HashMap<Long, Task>) tasks.readObject());
			for (Iterator i1 = game.getMapManager().getLoadedMaps().mapIterator(); i1.hasNext();) {
				GameMap map = (GameMap) game.getMapManager().getLoadedMaps().get((String) i1.next());
				for (Actor actor : map.mapActors.values()) {
					String actorPackage = actor.getClass().getCanonicalName();
					actor.setAnimations(AnimationHandler.buildActorAnimationSet(game, actorPackage));
				}
			}
			game.getMapManager().setCurrentMap(game, saveInfo.elementText("currentMap"));
		} finally {
			lmInput.close();
			tasks.close();
		}
	}

	public static void saveGame(Game game, String fileName) throws IOException {
		TFile zip = new TFile("res/save/" + fileName);
		if (zip.exists()) { zip.rm(); zip.mkdir(); }
		else { zip.mkdir(); }
		TFile saveInfo = new TFile("res/save/" + fileName + "/SaveInfo.xml");
		TFile loadedMaps = new TFile("res/save/" + fileName + "/LoadedMaps.ser");
		TFile scheduledTasks = new TFile("res/save/" + fileName + "/ScheduledTasks.ser");
		try {
			if (!saveInfo.exists()) { saveInfo.createNewFile(); }
			else { saveInfo.rm(); saveInfo.createNewFile(); }
			if (!loadedMaps.exists()) { loadedMaps.createNewFile(); }
			else { loadedMaps.rm(); loadedMaps.createNewFile(); }
			if (!scheduledTasks.exists()) { scheduledTasks.createNewFile(); }
			else { scheduledTasks.rm(); scheduledTasks.createNewFile(); }
		} catch (EOFException eofe) {
			eofe.printStackTrace();
		}
		XMLWriter xmlWriter = new XMLWriter(new TFileOutputStream(saveInfo), 
				OutputFormat.createPrettyPrint());
		ObjectOutputStream lmOutput = new ObjectOutputStream(new TFileOutputStream(loadedMaps));
		ObjectOutputStream tasks = new ObjectOutputStream(new TFileOutputStream(scheduledTasks));
		try {
			xmlWriter.write(makeSaveInfo(game, fileName));
			lmOutput.writeObject(game.getMapManager().getLoadedMaps());
			tasks.writeObject(game.getScheduler().getAllTasks());
		} catch (EOFException eofe) {
			eofe.printStackTrace();
		} finally {
			xmlWriter.flush();
			xmlWriter.close();
			lmOutput.flush();
			lmOutput.close();
			tasks.flush();
			tasks.close();
		}
	}

	public static ArrayList<Object> getSaveInfo(Game game, String fileName) throws 
	FileNotFoundException, DocumentException {
		ArrayList<Object> array = new ArrayList<Object>();
		Element saveInfo = saxReader.read(new TFileReader(
				new TFile("res/save/" + fileName + "/SaveInfo.xml"))).getRootElement();
		array.add(saveInfo.elementText("savedOn"));
		array.add(saveInfo.elementText("fileName"));
		return array;
	}

	private static Document makeSaveInfo(Game game, String fileName) {
		String savedOn = new SimpleDateFormat("MM-dd-yyyy").format(new Date()) 
				+ " at " + new SimpleDateFormat("hh:mm").format(new Date());
		Document saveInfo = DocumentHelper.createDocument();
		Element root = saveInfo.addElement("rpgsave").addAttribute("game", game.gameName);
		root.addElement("savedOn").addText(savedOn);
		root.addElement("currentMap").addText(game.getMapManager().getCurrentMapName());
		root.addElement("fileName").addText(fileName);
		return saveInfo;
	}

}
