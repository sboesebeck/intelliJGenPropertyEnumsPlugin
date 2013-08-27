package de.caluga.intellij.plugin.gpe;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorWriteActionHandler;
import com.intellij.psi.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeneratePropertyEnumsActionHandler extends EditorWriteActionHandler {
	@Override
	public void executeWriteAction(final Editor editor, final DataContext dataContext) {


		PsiHelper util = ApplicationManager.getApplication().getComponent(PsiHelper.class);
		JavaPsiFacade psiFacade = JavaPsiFacade.getInstance(editor.getProject());
		PsiElementFactory psiElementFactory = psiFacade.getElementFactory();
		PsiClass clazz = util.getCurrentClass(editor);

        PsiClass psiCls = psiElementFactory.createEnum("Fields");

		for (PsiField field : clazz.getFields()) {
            String fieldName = field.getName();
            String methodNameSuffix = util.capitalize(fieldName);
            String fieldType = field.getType().getCanonicalText();

            psiCls.add(psiElementFactory.createEnumConstantFromText(field.getName(),psiCls));

		}
        clazz.add(psiCls);
	}

}