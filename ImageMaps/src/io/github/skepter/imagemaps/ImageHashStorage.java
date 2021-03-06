package io.github.skepter.imagemaps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ImageHashStorage {

	private File file;

	public ImageHashStorage(JavaPlugin plugin) {
		file = new File(plugin.getDataFolder(), "maphashes.txt");
		if(!file.exists()) {
			plugin.getDataFolder().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				Bukkit.getLogger().severe("Could not create map hashing file!");
			}
		}
	}

	public HashMap<String, Short> get() {
		HashMap<String, Short> map = new HashMap<String, Short>();
		if (getFromFile() == null) {
			return map;
		}
		for (String str : getFromFile()) {
			map.put(str.split(":")[0], Short.valueOf(str.split(":")[1]));
		}
		return map;
	}

	private List<String> getFromFile() {
		try {
			List<String> lines = new ArrayList<String>();
			final BufferedReader in = new BufferedReader(
					new InputStreamReader(new FileInputStream(file)));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				lines.add(inputLine);
			}
			in.close();
			return lines;
		} catch (Exception e) {
			Bukkit.getLogger().severe("Could not get map hashes!");
		}
		return null;
	}

	public void store(Map<String, Short> maps) {
		file.delete();
		try {
			file.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			for(String str : maps.keySet()) {
				bw.write(str + ":" + maps.get(str));
				bw.newLine();
			}
			bw.flush();
			bw.close();
		} catch (IOException e) {
			Bukkit.getLogger().severe("Could not store map hashes!");
		}
	}

}
