package daomephsta.umbra.mcfunctions;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.Map.Entry;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Iterators;

import net.minecraft.command.FunctionObject;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.*;

//TODO Deprecate in 1.13
public class FunctionLoader
{
	private static final Logger logger = LogManager.getLogger(FunctionLoader.class);
	
	public static void loadFunctionsFor(MinecraftServer server, String modid)
	{
		if (server == null) return;
		WorldServer worldServer = (WorldServer) server.getEntityWorld();
		logger.debug("Loading functions from {}", modid);
		ModContainer container = Loader.instance().getActiveModList().stream()
			.filter(mc -> mc.getModId().equals(modid))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("No active mod found with mod ID " + modid));
		for (Entry<String, InputStream> entry : gatherInputStreams(container).entrySet())
		{
			ResourceLocation identifier = new ResourceLocation(modid, FilenameUtils.getBaseName(entry.getKey()));
			try(BufferedReader reader = new BufferedReader(new InputStreamReader(entry.getValue())))
			{
				List<String> lines = new ArrayList<>();
				String line = null;
				while((line = reader.readLine()) != null)
				{
					lines.add(line);
				}
				FunctionObject function = FunctionObject.create(worldServer.getFunctionManager(), lines);
				worldServer.getFunctionManager().getFunctions().put(identifier, function);
				logger.debug("Registered function for identifier {}", identifier);
			}
			catch (IOException e)
			{
				logger.error("Could not read file at {}", entry.getKey(), e);
			}
		}
	}

	private static Map<String, InputStream> gatherInputStreams(ModContainer container)
	{
		Map<String, InputStream> functionFileStreams = new HashMap<>();
		
		if (container.getSource().isDirectory())
		{
			try
			{
				File functionsDir = new File(container.getSource(), "assets/" + container.getModId() + "/functions");
				if (!functionsDir.exists())
				{
					logger.debug("Skipping {} as no functions folder was found in assets", container.getModId());
					return functionFileStreams;
				}
				for (Path path : Files.walk(functionsDir.toPath()).collect(Collectors.toList()))
				{
					if(Files.isRegularFile(path))
						functionFileStreams.put(path.toAbsolutePath().toString(), Files.newInputStream(path));
				}
			}
			catch (IOException e)
			{
				logger.error("Could not load functions for {} from {}", container.getName(), container.getSource(), e);
			}
		}
		else try(JarFile jar = new JarFile(container.getSource()))
		{
			for (Iterator<? extends JarEntry> jarIter = Iterators.forEnumeration(jar.entries()); jarIter.hasNext();)
			{
				JarEntry entry = jarIter.next();
				if (entry.getName().startsWith("/assets/" + container.getModId() + "/functions"))
					functionFileStreams.put(entry.getName(), jar.getInputStream(entry));
			}
		}
		catch (IOException e)
		{
			logger.error("Could not load functions for {} from {}", container.getName(), container.getSource(), e);
		}
		return functionFileStreams;
	}
}
