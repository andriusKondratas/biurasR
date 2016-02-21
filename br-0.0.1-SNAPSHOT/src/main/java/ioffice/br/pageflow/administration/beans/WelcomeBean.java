package ioffice.br.pageflow.administration.beans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.CloseEvent;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

@SuppressWarnings("serial")
@ManagedBean(name = "welcomeMB")
@ViewScoped
public class WelcomeBean implements Serializable {

	private DashboardModel model;
	private LineChartModel dateModel;
	private Map<String, LinkedList<String>> newsModel;

	public void loadModel() {
		model = new DefaultDashboardModel();

		loadDateModel();
		loadNewsModel();

		DashboardColumn column1 = new DefaultDashboardColumn();
		DashboardColumn column2 = new DefaultDashboardColumn();

		column1.addWidget("statistics");
		column2.addWidget("about");
		column2.addWidget("news");

		model.addColumn(column1);
		model.addColumn(column2);
	}

	private void loadDateModel() {
		dateModel = new LineChartModel();
		LineChartSeries series1 = new LineChartSeries();
		series1.setLabel("Series 1");

		series1.set("2014-01-01", 51);
		series1.set("2014-01-06", 22);
		series1.set("2014-01-12", 65);
		series1.set("2014-01-18", 74);
		series1.set("2014-01-24", 24);
		series1.set("2014-01-30", 51);

		LineChartSeries series2 = new LineChartSeries();
		series2.setLabel("Series 2");

		series2.set("2014-01-01", 32);
		series2.set("2014-01-06", 73);
		series2.set("2014-01-12", 24);
		series2.set("2014-01-18", 12);
		series2.set("2014-01-24", 74);
		series2.set("2014-01-30", 62);

		dateModel.addSeries(series1);
		dateModel.addSeries(series2);

		// dateModel.setTitle("Didinkite..");
		dateModel.setZoom(true);
		dateModel.getAxis(AxisType.Y).setLabel("Kiekis (t)");
		DateAxis axis = new DateAxis("Data");
		axis.setTickAngle(-50);
		axis.setMax("2014-02-01");
		axis.setTickFormat("%b %#d, %y");

		dateModel.getAxes().put(AxisType.X, axis);
	}

	private void loadNewsModel() {
		newsModel = new HashMap<String, LinkedList<String>>();
		String group = "";
		ApplicationContext ctx = new ClassPathXmlApplicationContext();
		Resource res = ctx.getResource("classpath:" + "releaseNotes.properties");
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(res.getInputStream()));
			String line;

			while ((line = reader.readLine()) != null) {
				if (line.startsWith("#")) {
					group = line.replace("#", "");
					newsModel.put(group, new LinkedList<String>());
				} else {
					newsModel.get(group).add(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleReorder(DashboardReorderEvent event) {
		FacesMessage message = new FacesMessage();
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		message.setSummary("Reordered: " + event.getWidgetId());
		message.setDetail("Item index: " + event.getItemIndex() + ", Column index: " + event.getColumnIndex() + ", Sender index: "
				+ event.getSenderColumnIndex());

		addMessage(message);
	}

	public void handleClose(CloseEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Panel Closed", "Closed panel id:'" + event.getComponent().getId() + "'");

		addMessage(message);
	}

	public void handleToggle(ToggleEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, event.getComponent().getId() + " toggled", "Status:" + event.getVisibility().name());

		addMessage(message);
	}

	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public DashboardModel getModel() {
		return model;
	}

	public LineChartModel getDateModel() {
		return dateModel;
	}

	public Map<String, LinkedList<String>> getNewsModel() {
		return newsModel;
	}

}