package spring.annotation.config;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

//定制过滤规则
public class MyTypeFilter implements TypeFilter {

	/**
	 * MetadataReader:读取到的当前正在扫描的类的信息
	 * MetadataReaderFactory：可以获取到其他类的信息
	 */
	public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
			throws IOException {
		// 获取当前类的注解信息
		AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
		//获取当前正在扫描的类的类信息
		ClassMetadata classMetadata = metadataReader.getClassMetadata();
		//获取当前类的资源信息(类的路径)
		Resource resource = metadataReader.getResource();
		
		String classname = classMetadata.getClassName();
		System.out.println(classname);
		
		if(classname.contains("er")){
			return true;
		}
		return false;
	}

}
