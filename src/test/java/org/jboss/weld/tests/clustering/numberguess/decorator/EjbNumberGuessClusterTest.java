package org.jboss.weld.tests.clustering.numberguess.decorator;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.weld.tests.clustering.numberguess.NumberGuessClusterTest;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
@RunAsClient
public class EjbNumberGuessClusterTest extends NumberGuessClusterTest {
	
	 public static WebArchive createTestDeployment() {
		 return ShrinkWrap.create(WebArchive.class, "weld-clustering-tests.war")
				 .addClasses(AutoReset.class, AutoResetInterceptor.class, Game.class, GameLocal.class,
						 MethodLogInterceptor.class, MethodLog.class, 
						 EjbGameBean.class, RandomStatelessEjb.class, GuessCheckDecorator.class, MaxNumber.class, MockGenerator.class, 
						 ProductionGenerator.class, Random.class)
				 .addAsWebResource("numberguess/home.xhtml", "home.xhtml")
				 .addAsWebResource("numberguess/index.html", "index.html")
				 .addAsWebResource("numberguess/template.xhtml", "template.xhtml")
				 .addAsWebInfResource("numberguess/WEB-INF/beans-decorator.xml", "beans.xml")
				 .addAsWebInfResource("numberguess/WEB-INF/faces-config.xml", "faces-config.xml")
				 .addAsWebInfResource("numberguess/WEB-INF/web.xml", "web.xml");
	 }
	
	 @Deployment(name = DEPLOYMENT1, managed=false, testable=false)
	 @TargetsContainer(CONTAINER1)
	 public static WebArchive createTestDeployment1() {
	    return createTestDeployment();
	 }
	    
	 @Deployment(name = DEPLOYMENT2, managed=false, testable=false)
	 @TargetsContainer(CONTAINER2)
	 public static WebArchive createTestDeployment2() {
	    return createTestDeployment()
	         .addAsWebInfResource(EmptyAsset.INSTANCE, "force-hashcode-change.txt");
	 }
}
