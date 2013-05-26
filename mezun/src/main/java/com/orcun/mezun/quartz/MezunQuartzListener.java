package com.orcun.mezun.quartz;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

import com.orcun.mezun.quartz.job.UserActivationReminder;

public class MezunQuartzListener implements ServletContextListener {

	private Scheduler scheduler;

	public void contextInitialized(ServletContextEvent sce) {

		try {

			SchedulerFactory sf = new StdSchedulerFactory();

			// scheduler=sf.getScheduler();
			setScheduler(sf.getScheduler());

			Calendar startTime = java.util.Calendar.getInstance();
			startTime.set(Calendar.HOUR_OF_DAY, 15);
			startTime.set(Calendar.MINUTE, 45);
			startTime.set(Calendar.SECOND, 0);
			startTime.set(Calendar.MILLISECOND, 0);

			if (startTime.getTime().before(new Date())) {
				startTime.add(Calendar.DAY_OF_MONTH, 1);
			}

			SimpleTrigger adminReminderTrigger = new SimpleTrigger(
					"adminReminderTrigger", "adminReminderGroup");
			adminReminderTrigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
			adminReminderTrigger.setStartTime(startTime.getTime());
			adminReminderTrigger.setRepeatInterval(24L * 60L * 60L * 1000L);

			
			JobDetail userActivationReminder = new JobDetail(
					"userActivationReminder", "adminReminderGroup",
					UserActivationReminder.class);
			
			getScheduler().start();
			// getScheduler().scheduleJob(syncCheck, syncTrigger);
			getScheduler().scheduleJob(userActivationReminder,
					adminReminderTrigger);

		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}

	public void contextDestroyed(ServletContextEvent sce) {
		try {
			getScheduler().shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

}
