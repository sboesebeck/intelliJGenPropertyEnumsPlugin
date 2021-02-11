package de.caluga.intellij.plugin.gpe;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorWriteActionHandler;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiField;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class GeneratePropertyEnumsActionHandler extends EditorWriteActionHandler {

    @Override
    public void executeWriteAction(@NotNull Editor editor, @Nullable Caret caret, DataContext dataContext) {
        PsiHelper util = ApplicationManager.getApplication().getComponent(PsiHelper.class);
        JavaPsiFacade psiFacade = JavaPsiFacade.getInstance(editor.getProject());
        PsiElementFactory psiElementFactory = psiFacade.getElementFactory();
        PsiClass clazz = util.getCurrentClass(editor);
        if (clazz.isEnum() && clazz.getName().equals("Fields")) {
            clazz = clazz.getContainingClass();
        }
        PsiClass psiCls = null;

        PsiClass existing = null;
        for (PsiClass c : clazz.getAllInnerClasses()) {
            if (c.isEnum() && c.getName() != null && c.getName().equals("Fields")) {
                existing = c;
                break;
            }
        }

        psiCls = psiElementFactory.createEnum("Fields");

        PsiField[] fields = clazz.getFields();
        Arrays.sort(fields, (o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
        for (PsiField field : fields) {
//            String fieldName = field.getName();
//            String methodNameSuffix = util.capitalize(fieldName);
//            String fieldType = field.getType().getCanonicalText();

            psiCls.add(psiElementFactory.createEnumConstantFromText(field.getName(), psiCls));

        }
        if (existing == null) {
            clazz.add(psiCls);
        } else {
            existing.replace(psiCls);
        }
    }

}