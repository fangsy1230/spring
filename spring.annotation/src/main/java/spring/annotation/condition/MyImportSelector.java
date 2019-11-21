package spring.annotation.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
//自定义逻辑返回需要导入的组件
public class MyImportSelector implements ImportSelector{

	/**
	 * AnnotationMetadata:当前标注@Import注解的类的所有注解信息
	 */
	public String[] selectImports(AnnotationMetadata annotationMetadata) {
		//方法不要返回null，可以是空数组
		return new String[]{"spring.annotation.bean.Yellow","spring.annotation.bean.Blue"};
	}

}
