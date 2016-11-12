package ioffice.br.pageflow.administration.beans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.annotation.PostConstruct;
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
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
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

	private Map<String, LinkedList<String>> newsModel;
	private BarChartModel barModel;

	@PostConstruct
	public void loadModel() {
		 createBarModel();
	}
	
	 private BarChartModel initBarModel() {
	        BarChartModel model = new BarChartModel();
	        
	        
	        ChartSeries series1 = new ChartSeries();

	        series1.set(0, 8);
	        series1.set(2, 1);
	        series1.set(4, 9);
	        series1.set(4.5, 2);
	        series1.set(9, 7);

	        ChartSeries series2 = new ChartSeries();

	        series2.set(0, 1);
	        series2.set(5, 9);
	        series2.set(5.5, 2);
	        series2.set(8, 8);
	        series2.set(9, 1);
	        
	        ChartSeries series3 = new ChartSeries();
	        
	        series3.set(1.5, 9);
	        series3.set(2, 7);
	        series3.set(7, 1);
	        series3.set(8, 5);
	        series3.set(9, 3);

	        model.addSeries(series1);
	        model.addSeries(series2);
	        model.addSeries(series3);
	        
	        model.setExtender("skinChart");
	        return model;
	    }
	 
	 
	 private void createBarModel() {
	        barModel = initBarModel();
	        
	        barModel.setTitle("Bar Chart");
	        
	        Axis xAxis = barModel.getAxis(AxisType.X);
	        
	        Axis yAxis = barModel.getAxis(AxisType.Y);
	        yAxis.setMin(0);
	        yAxis.setMax(10);
	        xAxis.setMin(0);
	        xAxis.setMax(8.5);
	    }


	/*private void loadNewsModel() {
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
	}*/

	public Map<String, LinkedList<String>> getNewsModel() {
		return newsModel;
	}

	public BarChartModel getBarModel() {
		return barModel;
	}

	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}
}