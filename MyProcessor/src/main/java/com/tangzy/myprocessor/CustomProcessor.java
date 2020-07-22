package com.tangzy.myprocessor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.tangzy.myannotation.CustomAnnotation;

import java.io.IOException;
import javax.lang.model.element.Modifier;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;


@AutoService(Processor.class)
public class CustomProcessor extends AbstractProcessor {

    //处理Element的工具类
    private Elements mElementUtils;
    //生成文件的工具
    private Filer mFiler;
    //日志信息的输出
    private Messager mMessager;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mElementUtils = processingEnv.getElementUtils();
        mFiler = processingEnv.getFiler();
        mMessager = processingEnv.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes(){
        Set<String> supportedType = new LinkedHashSet<String>();
        supportedType.add(CustomAnnotation.class.getCanonicalName());
        return supportedType;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //获取CustomAnnotation注解的类的集合
        Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(CustomAnnotation.class);
        for (Element element : set){
            if(element.getKind() == ElementKind.CLASS){
                TypeElement typeElement = (TypeElement) element;
                brewJavaFile(typeElement);
            }
        }
        return true;
    }

    private void brewJavaFile(TypeElement pElement){
        //sayHello 方法
        CustomAnnotation myAnnotation = pElement.getAnnotation(CustomAnnotation.class);
        MethodSpec methodSpec = MethodSpec.methodBuilder("sayHello")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC).returns(void.class)
                .addStatement("$T.out.println($S)",System.class,"Hello"+myAnnotation.value()).build();

        // class
        TypeSpec typeSpec = TypeSpec.classBuilder(pElement.getSimpleName().toString()+"$$HelloWorld").addModifiers(Modifier.PUBLIC,Modifier.FINAL).addMethod(methodSpec).build();
        // 获取包路径，把我们的生成的源码放置在与被注解类中同一个包路径中
        JavaFile javaFile = JavaFile.builder(mElementUtils.getPackageOf(pElement).getQualifiedName().toString(),typeSpec).build();
        try {
            javaFile.writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
