package uqbar.arena.persistence.reflection;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClasspathCrawler {
	private ClassLoader classLoader = null;
	private String[] classPaths;
	
	public ClasspathCrawler(ClassLoader classLoader) {
		super();
		this.classLoader = classLoader;
		this.classPaths = System.getProperty("java.class.path").split(File.pathSeparatorChar + "");
	}

	public Set<Class<?>> getClasses(List<String> packagesName) throws Exception{
	    Set<Class<?>> set = new HashSet<Class<?>>();
		for(String pkg:packagesName){
			set.addAll(this.getClasses(pkg));
		}
		return set;
	}
	
	public Set<Class<?>> getClasses(){
		return getClasses("");
	}
	
	public Set<Class<?>> getClasses(String packageName){
		try{
		    Set<File> set = new HashSet<File>();
		    Enumeration<URL> urls = this.classLoader.getResources(packageName);
		    while (urls.hasMoreElements()) {
		        URL url = urls.nextElement();
		        File dir = new File(url.getFile());
		        set.add(dir);
		    }
		    return crawl(set);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	protected Set<Class<?>> crawl(Set<File> set) throws Exception {
		Set<Class<?>> result = new HashSet<Class<?>>();

		for(File f:set){
			if(!f.exists())
				result.addAll(crawlJar(f));
			else
				result.addAll(crawlFiles(f));
		}
		
		return result;
	}
	
	protected Set<Class<?>> crawlFiles(File root) throws Exception {
		Set<Class<?>> result = new HashSet<Class<?>>();

		if(root.isFile()){
			if(isClass(root.getPath()))
				result.add(Class.forName(this.getClassNameFromFile(root.getPath())));			
		}else{
			for(File f:root.listFiles()){
				result.addAll(this.crawlFiles(f));
			}
		}
		
		return result;
	}
	

	protected String getClassNameFromFile(String path) throws Exception {
		for(String cp:this.classPaths){
			if(path.startsWith(cp)){
				return getClassName(path.substring(cp.length()));
			}
		}
			
		throw new Exception("Could not deduce className for:" + path);
	}

	protected Set<Class<?>> crawlJar(File f) throws Exception {
		Set<Class<?>> result = new HashSet<Class<?>>();
		
		String path = f.getPath().split("!")[0].substring(5);
		Enumeration<JarEntry> entries = new JarFile(path).entries();
		while(entries.hasMoreElements()){
			String entry = entries.nextElement().getName();
			if(isClass(entry)){
				result.add(Class.forName(this.getClassName(entry)));
			}
		}
		
		return result;
	}

	private boolean isClass(String entry) {
		return entry.endsWith(".class");
	}

	protected String getClassName(String path){
		String name = path;
		
		if(name.startsWith("/"))
			name = name.substring(1);

		name = name.split(".class")[0].replace("/", ".");
		
		return name;
	}
	
}
