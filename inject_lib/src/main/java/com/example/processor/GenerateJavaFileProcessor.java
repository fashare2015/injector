package com.example.processor;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-10-10
 * Time: 21:13
 * <br/><br/>
 */
@SupportedAnnotationTypes("com.example.Get") // 必须, 否则找不到注解, 等价于重写 getSupportedAnnotationTypes()
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class GenerateJavaFileProcessor extends AbstractProcessor {
    private Filer mOutputFiler;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mOutputFiler = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        ProcessorUtil.Companion.writeTo(roundEnv, mOutputFiler);
        return true;
    }
}