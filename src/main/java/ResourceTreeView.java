import org.primefaces.model.DefaultTreeNode;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.TreeNode;

@Named("treeBasicView")
@ViewScoped
public class ResourceTreeView implements Serializable {

    private TreeNode root;

    @PostConstruct
    public void init() {
        root = new DefaultTreeNode("Root", null);

        TreeNode restNode = new DefaultTreeNode("rest", root);

        TreeNode getNode = new DefaultTreeNode("get", restNode);
        TreeNode postNode = new DefaultTreeNode("post", restNode);

        TreeNode chainingTypeNode = new DefaultTreeNode ("chaining type (parameter)", getNode);

        TreeNode goalNode = new DefaultTreeNode("goal (parameter)", chainingTypeNode);

        postNode.getChildren().add(new DefaultTreeNode("trace"));
        postNode.getChildren().add(new DefaultTreeNode("chaining"));
        goalNode.getChildren().add(new DefaultTreeNode("fact (parameter)"));
    }

    public TreeNode getRoot() {
        return root;
    }
}
