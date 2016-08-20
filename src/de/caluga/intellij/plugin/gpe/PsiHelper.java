package de.caluga.intellij.plugin.gpe;


import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"ConstantConditions"})
public class PsiHelper implements ApplicationComponent {

    public PsiHelper() {
    }

    public void initComponent() {
    }

    public void disposeComponent() {
    }

    @NotNull
    public String getComponentName() {
        return getClass().getSimpleName();
    }

    public String convertCamelCase(String n) {
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < n.length() - 1; i++) {
            if (Character.isUpperCase(n.charAt(i)) && i > 0) {
                b.append("_");
            }
            b.append(n.substring(i, i + 1).toLowerCase());
        }
        b.append(n.substring(n.length() - 1));
        return b.toString();
    }

    public String createCamelCase(String n, boolean capitalize) {
        n = n.toLowerCase();
        String f[] = n.split("_");
        StringBuilder sb = new StringBuilder(f[0].substring(0, 1).toLowerCase());
        //String ret =
        sb.append(f[0].substring(1));
        for (int i = 1; i < f.length; i++) {
            sb.append(f[i].substring(0, 1).toUpperCase());
            sb.append(f[i].substring(1));
        }
        String ret = sb.toString();
        if (capitalize) {
            ret = ret.substring(0, 1).toUpperCase() + ret.substring(1);
        }
        return ret;
    }

    public String capitalize(String fieldName) {
        return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }


    public PsiClass getCurrentClass(final Editor editor) {
        if (editor == null) {
            return null;
        }
        PsiManager psiManager = PsiManager.getInstance(editor.getProject());
        VirtualFile vFile = FileDocumentManager.getInstance().getFile(editor.getDocument());
        PsiFile psiFile = psiManager.findFile(vFile);
        if (!(psiFile instanceof PsiJavaFile)) {
            return null;
        }
        PsiJavaFile javaFile = (PsiJavaFile) psiFile;
        PsiElement element = javaFile.findElementAt(editor.getCaretModel().getOffset());
        while (!(element instanceof PsiClass) && element != null) {
            element = element.getParent();
        }
        if (element == null) {
            return null;
        } else {
            return (PsiClass) element;
        }
    }
}
