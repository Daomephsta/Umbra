package daomephsta.umbra.annotationprocessing.processors.modelgen;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic.Kind;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import com.google.auto.service.AutoService;

import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("daomephsta.umbra.modelgen.annotations.GenerateSimpleModel")
public class SimpleModelGeneratingProcessor extends AbstractProcessor
{
	private static TypeMirror ITEM, BLOCK;
	
	@Override
	public synchronized void init(ProcessingEnvironment processingEnv)
	{
		super.init(processingEnv);

		ITEM = processingEnv.getElementUtils().getTypeElement("net.minecraft.item.Item").asType();
		BLOCK = processingEnv.getElementUtils().getTypeElement("net.minecraft.block.Block").asType();
	}
	
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
	{
		for(TypeElement annotation : annotations)
		{
			Collection<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
			for (Element annotated : annotatedElements)
			{
				if (annotated.getKind() == ElementKind.FIELD)
				{
					try
					{
						String registryName = getRegistryName(annotated);
						String domain = registryName.split(":")[0];
						String path = registryName.split(":")[1];
						ModelType type = getModelType(annotated);
						String modelLocation = String.join("/", "assets", domain, 
							"models", type.getModelSubfolder(), path + ".json");
						FileObject modelFile = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", modelLocation, annotated);
						processingEnv.getMessager().printMessage(Kind.NOTE, "Generated " + modelFile.getName());
						Writer w = modelFile.openWriter();
						String textureLocation = domain + ":" + type.getTextureSubfolder() + "/" + path;
						w.write(type.generateModel(textureLocation, e -> printExceptionToMessager(e, annotated)));
						w.close();
					}
					catch (IOException e)
					{
						printExceptionToMessager(e, annotated);
					}
				}
			}
		}
		return true;
	}
	
	private void printExceptionToMessager(Exception e, Element associated)
	{
		StringBuilder errorBuilder = 
			new StringBuilder(e.getClass().getSimpleName()).append(System.lineSeparator())
			.append(e.getMessage()).append(System.lineSeparator())
			.append("StackTrace:").append(System.lineSeparator());
		int lines = 0;
		for (StackTraceElement element : e.getStackTrace())
		{
			errorBuilder.append(element).append(System.lineSeparator());
			lines++;
			if (lines > 5)
			{
				errorBuilder.append("...").append(System.lineSeparator());
				break;
			}
		}
		if (e.getCause() != null)
		{
			errorBuilder
				.append("Cause:").append(System.lineSeparator())
				.append(e.getCause()).append(System.lineSeparator());
		}
		processingEnv.getMessager().printMessage(Kind.ERROR, errorBuilder.toString(), associated);
	}
	
	private ModelType getModelType(Element element)
	{
		if (processingEnv.getTypeUtils().isSubtype(element.asType(), ITEM))
			return ModelType.ITEM;
		else if (processingEnv.getTypeUtils().isSubtype(element.asType(), BLOCK))
			return ModelType.BLOCK;
		else
		{
			processingEnv.getMessager().printMessage(Kind.ERROR, "Could not determine type of " + element, element);
			throw new RuntimeException("SimpleModelGenerating processor has encountered a fatal error, see compiler output for details.");
		}
	}
	
	private String getRegistryName(Element element)
	{
		ObjectHolder fieldObjectHolder = element.getAnnotation(ObjectHolder.class); 
		ObjectHolder classObjectHolder = element.getEnclosingElement().getAnnotation(ObjectHolder.class);
		if (fieldObjectHolder == null && classObjectHolder == null)
		{
			processingEnv.getMessager().printMessage(Kind.ERROR, 
				"Could not find @ObjectHolder annotation on the field or its enclosing class", element);
			return null;
		}
		StringBuilder registryName = new StringBuilder();
		if (classObjectHolder != null)
			registryName.append(classObjectHolder.value()).append(':');
		if (fieldObjectHolder != null)
			registryName.append(fieldObjectHolder.value());
		else registryName.append(element.getSimpleName().toString().toLowerCase(Locale.ROOT));
		
		return registryName.toString();
	}
}

enum ModelType
{
	BLOCK("block", "block", "/blockCubeAll.template"),
	ITEM("item", "item", "/itemGenerated.template");
	
	private final String modelSubfolder, textureSubfolder;
	private final String modelTemplateResource; 
	private String modelTemplate;
	
	private ModelType(String modelSubfolder, String textureSubfolder, String modelTemplateResource)
	{
		this.modelSubfolder = modelSubfolder;
		this.textureSubfolder = textureSubfolder;
		this.modelTemplateResource = modelTemplateResource;
	}

	public String getModelSubfolder()
	{
		return modelSubfolder;
	}
	
	public String getTextureSubfolder()
	{
		return textureSubfolder;
	}
	
	public String generateModel(String textureLocation, Consumer<Exception> exceptionHandler)
	{
		if (modelTemplate == null)
		{
			StringBuilder modelTemplateBuilder = new StringBuilder();
			try(BufferedReader r = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(modelTemplateResource))))
			{
				String line;
				while ((line = r.readLine()) != null)
				{
					modelTemplateBuilder.append(line).append(System.lineSeparator());
				}
			}
			catch (Exception e)
			{
				exceptionHandler.accept(e);
			}
			modelTemplate = modelTemplateBuilder.toString();
		}
		return modelTemplate.replaceAll("@texture", textureLocation);
	}
}
