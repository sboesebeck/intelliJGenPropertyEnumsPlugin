package de.caluga.intellij.plugin.gpe;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.psi.PsiClass;


public class GeneratePropertyEnums extends EditorAction {

    public GeneratePropertyEnums() {
        super(new GeneratePropertyEnumsActionHandler());
    }

    protected GeneratePropertyEnums(EditorActionHandler defaultHandler) {
        super(defaultHandler);
    }

    public void update(Editor editor, Presentation presentation, DataContext dataContext) {
        PsiHelper util = ApplicationManager.getApplication()
                .getComponent(PsiHelper.class);
        /* figure out when to display the option to generate chained accessors */
        if (editor == null) {
            presentation.setVisible(false);
            return;
        }
        PsiClass javaClass = util.getCurrentClass(editor);
        if (javaClass == null || javaClass.isInterface()) {
            presentation.setVisible(false);
        } else {
            presentation.setVisible(true);
        }
    }


}
