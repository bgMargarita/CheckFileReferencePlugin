package com.intellij.codeInspection;

import com.intellij.codeInsight.daemon.GroupNames;
import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.*;




public class CheckingFileReferencesInspection extends BaseJavaLocalInspectionTool {
    private static final Logger LOG = Logger.getInstance("#CheckingFileReferencesInspection");


    private final LocalQuickFix myQuickFix = new MyQuickFix();

    @SuppressWarnings({"WeakerAccess"})
    @NonNls
    public String CHECKED_CLASSES = "java.lang.String;java.util.Date";
    @NonNls
    private static final String DESCRIPTION_TEMPLATE =
            "File does not exist in classpath";

}
