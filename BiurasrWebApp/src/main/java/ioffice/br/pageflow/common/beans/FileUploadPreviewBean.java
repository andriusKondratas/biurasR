package ioffice.br.pageflow.common.beans;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;

@ManagedBean(name = "fileMB")
@SessionScoped
public class FileUploadPreviewBean extends BasicBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	DefaultStreamedContent imagePreview;
	byte[] imageContent;


	@PostConstruct
	public void init() {
		imagePreview = new DefaultStreamedContent();
	}


	public DefaultStreamedContent getImagePreview() {
		if(imageContent != null){
			return new DefaultStreamedContent(new ByteArrayInputStream(imageContent));
		}
		return new DefaultStreamedContent(); 
	}


	public byte[] getImageContent() {
		return imageContent;
	}


	public void setImageContent(byte[] imageContent) {
		this.imageContent = imageContent;
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		imageContent = event.getFile().getContents();
	}

	
}
