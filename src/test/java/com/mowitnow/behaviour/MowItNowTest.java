package com.mowitnow.behaviour;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.ParameterConverters;
import org.junit.runner.RunWith;

import de.codecentric.jbehave.junit.monitoring.JUnitReportingRunner;

/**
 * Behaviour driven test.
 * Will test scenarios described in src/test/resources/com/matthieublanc/mowitnow/behaviour/mow_it_now_test.story
 * parsed with MowItNowSteps
 */
@RunWith(JUnitReportingRunner.class)
public class MowItNowTest extends JUnitStory  {

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
		return new InstanceStepsFactory(configuration(), new MowItNowSteps());
	}

}
