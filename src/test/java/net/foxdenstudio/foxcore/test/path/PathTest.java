package net.foxdenstudio.foxcore.test.path;

import com.google.inject.Guice;
import com.google.inject.Injector;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.path.components.*;
import net.foxdenstudio.foxcore.api.path.factory.*;
import net.foxdenstudio.foxcore.guice.module.ExceptionModule;
import net.foxdenstudio.foxcore.guice.module.PathModule;
import net.foxdenstudio.foxcore.guice.module.TestModule;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class PathTest {

    private static Injector injector;

    @BeforeClass
    public static void setInjector() {
        injector = Guice.createInjector(
                PathModule.INSTANCE,
                ExceptionModule.INSTANCE,
                TestModule.INSTANCE
        );
    }


    @Test
    public void objectPathFactory_givenValidInput_createsPath() throws FoxCommandException {
        FoxObjectPathFactory pathFactory = injector.getInstance(FoxObjectPathFactory.class);
        FoxObjectPath path;

        path = pathFactory.getPath("awoo");
        assertEquals("toString()", "awoo", path.toString());
        assertEquals("numElements()", 1, path.numElements());
        assertEquals("getElement(0)", "awoo", path.getElement(0));

        path = pathFactory.getPath("awoo/yip/bark");
        assertEquals("toString()", "awoo/yip/bark", path.toString());
        assertEquals("numElements()", 3, path.numElements());
        assertEquals("getElement(0)", "awoo", path.getElement(0));
        assertEquals("getElement(1)", "yip", path.getElement(1));
        assertEquals("getElement(2)", "bark", path.getElement(2));
    }

    @Test
    public void namespacePathFactory_givenValidInput_createsPath() throws FoxCommandException {
        FoxNamespacePathFactory pathFactory = injector.getInstance(FoxNamespacePathFactory.class);
        FoxNamespacePath path;

        path = pathFactory.getPath("");
        assertEquals("toString()", "", path.toString());
        assertTrue("isEmpty()", path.isEmpty());
        assertEquals("numElements()", 0, path.numElements());

        path = pathFactory.getPath("/");
        assertEquals("toString()", "", path.toString());
        assertTrue("isEmpty()", path.isEmpty());
        assertEquals("numElements()", 0, path.numElements());

        path = pathFactory.getPath("//");
        assertEquals("toString()", "", path.toString());
        assertTrue("isEmpty()", path.isEmpty());
        assertEquals("numElements()", 0, path.numElements());

        path = pathFactory.getPath("awoo");
        assertEquals("toString()", "awoo", path.toString());
        assertFalse("isEmpty()", path.isEmpty());
        assertEquals("numElements()", 1, path.numElements());
        assertEquals("getElement(0)", "awoo", path.getElement(0));

        path = pathFactory.getPath("awoo/yip/bark");
        assertEquals("toString()", "awoo/yip/bark", path.toString());
        assertFalse("isEmpty()", path.isEmpty());
        assertEquals("numElements()", 3, path.numElements());
        assertEquals("getElement(0)", "awoo", path.getElement(0));
        assertEquals("getElement(1)", "yip", path.getElement(1));
        assertEquals("getElement(2)", "bark", path.getElement(2));
    }

    @Test
    public void linkPathFactory_givenValidInput_createsPath() throws FoxCommandException {
        FoxLinkPathFactory pathFactory = injector.getInstance(FoxLinkPathFactory.class);
        FoxLinkPath path;

        path = pathFactory.getPath("");
        assertEquals("toString()", "", path.toString());
        assertTrue("isEmpty()", path.isEmpty());
        assertEquals("numElements()", 0, path.numElements());

        path = pathFactory.getPath("/");
        assertEquals("toString()", "", path.toString());
        assertTrue("isEmpty()", path.isEmpty());
        assertEquals("numElements()", 0, path.numElements());

        path = pathFactory.getPath("//");
        assertEquals("toString()", "", path.toString());
        assertTrue("isEmpty()", path.isEmpty());
        assertEquals("numElements()", 0, path.numElements());

        path = pathFactory.getPath("awoo");
        assertEquals("toString()", "awoo", path.toString());
        assertFalse("isEmpty()", path.isEmpty());
        assertEquals("numElements()", 1, path.numElements());
        assertEquals("getElement(0)", "awoo", path.getElement(0));

        path = pathFactory.getPath("awoo/yip/bark");
        assertEquals("toString()", "awoo/yip/bark", path.toString());
        assertFalse("isEmpty()", path.isEmpty());
        assertEquals("numElements()", 3, path.numElements());
        assertEquals("getElement(0)", "awoo", path.getElement(0));
        assertEquals("getElement(1)", "yip", path.getElement(1));
        assertEquals("getElement(2)", "bark", path.getElement(2));
    }

    @Test
    public void indexPathFactory_givenValidInput_createsPath() throws FoxCommandException {
        FoxIndexPathFactory pathFactory = injector.getInstance(FoxIndexPathFactory.class);
        FoxIndexPath path;

        path = pathFactory.getPath("");
        assertEquals("toString()", "", path.toString());
        assertTrue("isEmpty()", path.isEmpty());
        assertEquals("getIndexType()", "", path.getIndexType());
        assertNotNull("getNamespacePath()", path.getNamespacePath());
        assertTrue("namespace isEmpty()", path.getNamespacePath().isEmpty());
        assertEquals("namespace numElements()", 0 , path.getNamespacePath().numElements());

        path = pathFactory.getPath("awoo");
        assertEquals("toString()", "awoo", path.toString());
        assertFalse("isEmpty()", path.isEmpty());
        assertEquals("getIndexType()", "awoo", path.getIndexType());
        assertNotNull("getNamespacePath()", path.getNamespacePath());
        assertTrue("namespace isEmpty()", path.getNamespacePath().isEmpty());
        assertEquals("namespace numElements()", 0 , path.getNamespacePath().numElements());

        path = pathFactory.getPath("awoo/yip/bark");
        assertEquals("toString()", "awoo/yip/bark", path.toString());
        assertFalse("isEmpty()", path.isEmpty());
        assertEquals("getIndexType()", "awoo", path.getIndexType());
        assertNotNull("getNamespacePath()", path.getNamespacePath());
        assertFalse("namespace isEmpty()", path.getNamespacePath().isEmpty());
        assertEquals("namespace numElements()", 2 , path.getNamespacePath().numElements());
        assertEquals("namespace getElement(0)", "yip", path.getNamespacePath().getElement(0));
        assertEquals("namespace getElement(0)", "bark", path.getNamespacePath().getElement(1));
    }

    @Test
    public void fullPathFactory_givenValidInput_createsPath() throws FoxCommandException {
        FoxFullPathFactory fullPathFactory = injector.getInstance(FoxFullPathFactory.class);
        FoxFullPath path;

        path = fullPathFactory.getPath("awoo");
        assertEquals("toString()", "awoo", path.toString());
        assertNotNull("getIndexPath()", path.getIndexPath());
        assertNotNull("getObjectPath()", path.getObjectPath());
        assertNotNull("getLinkPaths()", path.getLinkPaths());
        assertTrue("index isEmpty()", path.getIndexPath().isEmpty());
        assertTrue("index namespace isEmpty()", path.getIndexPath().getNamespacePath().isEmpty());
        assertEquals("index namespace numElements()", 
                0 , path.getIndexPath().getNamespacePath().numElements());
        assertEquals("object numElements()", 1 , path.getObjectPath().numElements());
        assertEquals("object getElement(0)", "awoo", path.getObjectPath().getElement(0));
        assertTrue("links isEmpty()", path.getLinkPaths().isEmpty());

        path = fullPathFactory.getPath("@awoo/bark:a/fox:a/wolf:a/snep");
        assertEquals("toString()", "@awoo/bark:a/fox:a/wolf:a/snep", path.toString());
        assertNotNull("getIndexPath()", path.getIndexPath());
        assertNotNull("getObjectPath()", path.getObjectPath());
        assertNotNull("getLinkPaths()", path.getLinkPaths());
        assertFalse("index isEmpty()", path.getIndexPath().isEmpty());
        assertFalse("index namespace isEmpty()", path.getIndexPath().getNamespacePath().isEmpty());
        assertEquals("index namespace numElements()",
                1 , path.getIndexPath().getNamespacePath().numElements());
        assertEquals("object numElements()", 2 , path.getObjectPath().numElements());
        assertEquals("object getElement(0)", "a", path.getObjectPath().getElement(0));
        assertEquals("object getElement(1)", "fox", path.getObjectPath().getElement(1));
        assertFalse("links isEmpty()", path.getLinkPaths().isEmpty());
        assertEquals("links size()", 2, path.getLinkPaths().size());
        assertFalse("links[0] isEmpty()", path.getLinkPaths().get(0).isEmpty());
        assertFalse("links[1] isEmpty()", path.getLinkPaths().get(1).isEmpty());
        assertEquals("links[0] getElement(0)", "a", path.getLinkPaths().get(0).getElement(0));
        assertEquals("links[0] getElement(1)", "wolf", path.getLinkPaths().get(0).getElement(1));
        assertEquals("links[1] getElement(0)", "a", path.getLinkPaths().get(1).getElement(0));
        assertEquals("links[1] getElement(1)", "snep", path.getLinkPaths().get(1).getElement(1));

    }

}
