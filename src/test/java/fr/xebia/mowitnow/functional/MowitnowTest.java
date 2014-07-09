package fr.xebia.mowitnow.functional;

import java.util.Arrays;
import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.ParameterConverters;
import org.junit.runner.RunWith;

import de.codecentric.jbehave.junit.monitoring.JUnitReportingRunner;

@RunWith(JUnitReportingRunner.class)
public class MowitnowTest extends JUnitStories {

	@Override
	public Configuration configuration() {
		Configuration configuration = new MostUsefulConfiguration().useStoryLoader(new LoadFromClasspath())
				.useStoryReporterBuilder(new StoryReporterBuilder().withDefaultFormats().withFormats(Format.CONSOLE, Format.TXT))
				.usePendingStepStrategy(new FailingUponPendingStep());
		configuration.parameterConverters().addConverters(new ParameterConverters.EnumConverter());
		return configuration;
	}

	@Override
	public InjectableStepsFactory stepsFactory() {
		return new InstanceStepsFactory(configuration(), new MowitnowSteps() , new MowerControlSteps());
	}

	@Override
	protected List<String> storyPaths() {
        return new StoryFinder()
        .findPaths(CodeLocations.codeLocationFromClass(this.getClass()).getFile(), Arrays.asList("**/*.story"), null);	}

}
