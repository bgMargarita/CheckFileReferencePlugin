package com.intellij.codeInspection;


import com.intellij.codeInsight.daemon.impl.HighlightInfo;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.testFramework.UsefulTestCase;
import com.intellij.testFramework.builders.JavaModuleFixtureBuilder;
import com.intellij.testFramework.fixtures.*;
import junit.framework.Assert;

import java.util.List;

/**
 * @see JavaCodeInsightFixtureTestCase
 * @see LightCodeInsightFixtureTestCase
 */
public class TestThisPlugin extends UsefulTestCase {

    protected CodeInsightTestFixture myFixture;


    public void setUp() throws Exception {

        final IdeaTestFixtureFactory fixtureFactory = IdeaTestFixtureFactory.getFixtureFactory();
        final TestFixtureBuilder<IdeaProjectTestFixture> testFixtureBuilder =
                fixtureFactory.createFixtureBuilder(getName());
        myFixture = JavaTestFixtureFactory.getFixtureFactory().createCodeInsightFixture(testFixtureBuilder.getFixture());
        myFixture.setTestDataPath("src/test/com/intellij/codeInspection");
        final JavaModuleFixtureBuilder builder = testFixtureBuilder.addModule(JavaModuleFixtureBuilder.class);

        builder.addContentRoot(myFixture.getTempDirPath()).addSourceRoot("");
        builder.setMockJdkLevel(JavaModuleFixtureBuilder.MockJdkLevel.jdk15);
        myFixture.setUp();
    }

    public void tearDown() throws Exception {
        myFixture.tearDown();
        myFixture = null;
    }

    protected void doTest(String testName, String hint) throws Throwable {
        myFixture.configureByFile(testName + ".java");
        //noinspection unchecked
        myFixture.enableInspections(com.intellij.codeInspection.CheckingFileReferencesInspection.class);
        List<HighlightInfo> highlightInfos = myFixture.doHighlighting();
        Assert.assertTrue(!highlightInfos.isEmpty());

        final IntentionAction action = myFixture.findSingleIntention(hint);

        Assert.assertNotNull(action);
        myFixture.launchAction(action);
        myFixture.checkResultByFile(testName + ".java");
    }

    // Test the "==" case
    public void test() throws Throwable {
        doTest("test1", "File does not exist");
    }
}
