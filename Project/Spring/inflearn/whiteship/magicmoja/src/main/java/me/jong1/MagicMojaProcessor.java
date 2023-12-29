package me.jong1;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

@AutoService(Processor.class)
public class MagicMojaProcessor extends AbstractProcessor {
	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return Set.of(Magic.class.getName());
	}

	//TypeElement?? Package Element, Class Element, Method Element등등 소스코드의 구성요소를 Element라고 한다.
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Magic.class);

		for (Element element : elements) {
			//if(element.getKind().isInterface()){
			if (element.getKind() != ElementKind.INTERFACE) {
				processingEnv.getMessager()
					.printMessage(Diagnostic.Kind.ERROR,
						"Magic Annotation can not be used on " + element.getSimpleName()); // Interface가 아닌 경우 에러로 처리
			} else { // 로그를 남기고자 할 떄
				processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Processing " + element.getSimpleName());
			}

			TypeElement typeElement = (TypeElement)element;
			ClassName className = ClassName.get(typeElement);

			//하위 두개는 메모리상에 객체로만 클래스르 정의한 것

			//PullOut Method제작
			MethodSpec pullOut = MethodSpec.methodBuilder("pullOut")
				.addModifiers(Modifier.PUBLIC)
				.returns(String.class)
				.addStatement("return $S", "Rabbit")
				.build();

			//클래스의 풀패키지 경로가 아니라 SimpleName만 기록하면 된다.
			TypeSpec magicMoja = TypeSpec.classBuilder("MagicMoja")
				.addModifiers(Modifier.PUBLIC)
				.addMethod(pullOut)
				.addSuperinterface(className)
				.build();

			//getFiler가 소스코드, 클래스코드 및 리소스를 생성 할 수 있는 인터페이스 이다.
			Filer filer = processingEnv.getFiler();
			try {
				JavaFile.builder(className.packageName(), magicMoja)
					.build()
					.writeTo(filer);
			} catch (IOException e) {
				processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "FATAL ERROR: " + e);
			}
		}
		return false;
	}
}
