package com.intellij.codeInspection;

import com.intellij.codeInsight.daemon.GroupNames;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.ui.DocumentAdapter;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.io.File;
import java.util.StringTokenizer;


public class CheckingFileReferencesInspection extends BaseJavaLocalInspectionTool {
    private static final Logger LOG = Logger.getInstance("#CheckingFileReferencesInspection");


    private final LocalQuickFix myQuickFix = new MyQuickFix();

    @SuppressWarnings({"WeakerAccess"})
    @NonNls
    public String CHECKED_CLASSES = "java.lang.String;java.util.Date";
    @NonNls
    private static final String DESCRIPTION_TEMPLATE =
            "File does not exist in classpath";

    @NotNull
    public String getDisplayName() {

        return "'==' or '!=' instead of 'equals()'";
    }

    @NotNull
    public String getGroupDisplayName() {
        return GroupNames.BUGS_GROUP_NAME;
    }

    @NotNull
    public String getShortName() {
        return "CheckingFileReferences";
    }

    private boolean isCheckedType(PsiType type) {
        if (!(type instanceof PsiClassType)) return false;

        StringTokenizer tokenizer = new StringTokenizer(CHECKED_CLASSES, ";");
        while (tokenizer.hasMoreTokens()) {
            String className = tokenizer.nextToken();
            if (type.equalsToText(className)) return true;
        }

        return false;
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly) {
        return new JavaElementVisitor() {

            @Override
            public void visitReferenceExpression(PsiReferenceExpression psiReferenceExpression) {
            }


            @Override
            public void visitStatement(PsiStatement statement) {
                super.visitStatement(statement);
                //TODO
                String javaText = statement.getText();
                if (javaText.contains(".property") || javaText.contains(".txt") || javaText.contains(".java")) {
                    holder.registerProblem(statement, DESCRIPTION_TEMPLATE, myQuickFix);
                }

            }

          /*  @Override
            public void visitBlockStatement(PsiBlockStatement statement) {
                super.visitBlockStatement(statement);
                //TODO
                PsiStatement[] statements = statement.getCodeBlock().getStatements();
                for (PsiStatement statementObj : statements) {
                    String javaText = statementObj.getText();
                    if (javaText.contains(".property")) {
                        holder.registerProblem(statement, DESCRIPTION_TEMPLATE, myQuickFix);
                    }
                }
            }*/
        };
    }


    private static class MyQuickFix implements LocalQuickFix {
        @NotNull
        public String getName() {
            return "File does not exist";
        }

        public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {

        }

        @NotNull
        public String getFamilyName() {
            return getName();
        }
    }

    public JComponent createOptionsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        final JTextField checkedClasses = new JTextField(CHECKED_CLASSES);
        checkedClasses.getDocument().addDocumentListener(new DocumentAdapter() {
            public void textChanged(DocumentEvent event) {
                CHECKED_CLASSES = checkedClasses.getText();
            }
        });

        panel.add(checkedClasses);
        return panel;
    }

    public boolean isEnabledByDefault() {


        return true;
    }
}
