package ioffice.br.config;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Configuration
@PropertySources({ @PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = false) })
@Service("ApplicationProperties")
@Scope("singleton")
public class ApplicationProperties implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private @Value("${physical.delete.enabled}") boolean physicalDeleteEnabled;
	private @Value("${priviledge.check.enabled}") boolean priviledgeCheckEnabled;
	private @Value("${year.list.min}") int yearListMin;
	private @Value("${year.list.max}") int yearListMax;
	
	private @Value("${table.paginatorTemplate}") String tablePaginatorTemplate;
	private @Value("${table.rowsPerPageTemplate}") String tableRowsPerPageTemplate;
	
	private @Value("${environment.type}") String environmentType;
	private @Value("${environment.name}") String environmentName;
	private @Value("${project.version}") String projectVersion;
	
	@SuppressWarnings("unused")
	private boolean devEnvironment;

	public int getYearListMin() {
		return yearListMin;
	}

	public int getYearListMax() {
		return yearListMax;
	}

	public boolean isPhysicalDeleteEnabled() {
		return physicalDeleteEnabled;
	}

	public boolean isPriviledgeCheckEnabled() {
		return priviledgeCheckEnabled;
	}

	public String getEnvironmentType() {
		return environmentType;
	}
	
	public String getEnvironmentName() {
		return environmentName;
	}

	public String getProjectVersion() {
		return projectVersion;
	}
	
	public String getTablePaginatorTemplate() {
		return tablePaginatorTemplate;
	}

	public String getTableRowsPerPageTemplate() {
		return tableRowsPerPageTemplate;
	}

	/*Custom*/
	public boolean isDevEnvironment() {
		return getEnvironmentType().equals("dev");
	}

	public void setDevEnvironment(boolean devEnvironment) {
		this.devEnvironment = devEnvironment;
	}

	
	
	
}
