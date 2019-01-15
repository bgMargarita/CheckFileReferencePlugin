package com.intellij.codeInspection;


import com.intellij.codeInspection.InspectionToolProvider;
import org.jetbrains.annotations.NotNull;


public class CheckingFileReferencesProvider implements InspectionToolProvider {
    @NotNull
    public Class[] getInspectionClasses() {
        return new Class[]{CheckingFileReferencesInspection.class};
    }

}
