package ioffice.br.pageflow.administration.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import ioffice.br.pageflow.common.beans.BasicBean;
import ioffice.br.pageflow.common.enums.ObjectState;
import ioffice.br.persistance.enums.DomainObjectType;
import ioffice.br.persistance.enums.DomainType;
import ioffice.br.persistance.enums.PriviledgeType;
import ioffice.br.persistance.model.administration.DomainObject;
import ioffice.br.persistance.model.administration.Role;
import ioffice.br.persistance.model.administration.RolePriviledge;
import ioffice.br.persistance.service.administration.AdministrationObjectCache;
import ioffice.br.persistance.service.administration.RoleService;

import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;

@ManagedBean(name = "roleEditMB")
@ViewScoped
public class RoleEditBean extends BasicBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{RoleService}")
	RoleService roleService;

	@ManagedProperty(value = "#{AdministrationObjectCache}")
	AdministrationObjectCache administrationObjectCache;

	Role role;

	TreeNode rolePriviledges;
	TreeNode[] selectedRolePriviledges;

	public void save() {
		try {
			List<RolePriviledge> rolePriviledgesToPersist = new ArrayList<RolePriviledge>();
			extractRolePriviledges(rolePriviledges, rolePriviledgesToPersist);
			role.setRolePriviledges(rolePriviledgesToPersist);
			roleService.saveOrUpdate(role);
		} catch (Exception e) {
			facesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Klaida", e.toString()));
			return;
		}
		navigate("role-list");
	}

	public void delete() {
		try {
			role.setDeleted(true);
			roleService.saveOrUpdate(role);
		} catch (Exception e) {
			facesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Klaida", e.toString()));
			return;
		}
		navigate("role-list");
	}

	@Override
	public void loadModel() {
		super.loadModel();
		String roleId = (String) flashScope().get("roleId");
		if (roleId != null) {
			this.role = roleService.findById(Long.valueOf(roleId));
			this.objectState = ObjectState.OLD;
		} else {
			this.role = new Role();
			this.role.setRolePriviledges(new ArrayList<RolePriviledge>());
			this.objectState = ObjectState.NEW;
		}

		this.rolePriviledges = loadRolePriviledges();
	}

	private TreeNode loadRolePriviledges() {
		TreeNode rootNode = new CheckboxTreeNode("ROOT", null, null);
		for (DomainType domainType : DomainType.values()) {
			if (domainType.getParentDomain() == null) {
				TreeNode domainNodeL1 = new CheckboxTreeNode("DOM", new RolePriviledge(getMessage(domainType.getInlLabel()), "domNodel1"), rootNode);
				for (DomainType domainTypeChild : domainType.getChildDomains()) {
					TreeNode domainNodeL2 = new CheckboxTreeNode("DOM", new RolePriviledge(getMessage(domainTypeChild.getInlLabel()), "domNodel2"),
							domainNodeL1);
					// Objects attached to children
					mapDomainObjects(domainTypeChild, domainNodeL2);
				}
				// Objects attached directly
				mapDomainObjects(domainType, domainNodeL1);
			}
		}
		return rootNode;
	}

	private void mapDomainObjects(DomainType domainType, TreeNode parentNode) {
		for (DomainObject domainObject : administrationObjectCache.getAllDomainObjects()) {
			if (domainType.equals(domainObject.getCode().getDomain()) && !domainObject.getCode().isHidden()) {
				TreeNode domainObjectNode = new CheckboxTreeNode("OBJ", new RolePriviledge(getMessage(domainObject.getCode().getInlLabel()), "objNode"),
						parentNode);
				for (PriviledgeType priviledgeType : PriviledgeType.values()) {
					RolePriviledge rolePriviledge = matchRolePriviledge(domainObject, priviledgeType);
					TreeNode priviledgeNode = new CheckboxTreeNode("PRIV", rolePriviledge, domainObjectNode);
					priviledgeNode.setSelected(rolePriviledge.isActive());
				}
			}
		}
	}

	private void extractRolePriviledges(TreeNode rootNode, List<RolePriviledge> rolePriviledgesToPersist) {
		for (TreeNode node : rootNode.getChildren()) {
			if (node.isLeaf() && node.getData() != null && node.getType().equals("PRIV")) {
				RolePriviledge priv = (RolePriviledge) node.getData();
				priv.setActive(node.isSelected());
				rolePriviledgesToPersist.add(priv);
			}
			extractRolePriviledges(node, rolePriviledgesToPersist);
		}
	}

	private RolePriviledge matchRolePriviledge(DomainObject domainObject, PriviledgeType priviledgeType) {
		RolePriviledge priviledge = null;

		for (RolePriviledge priv : this.role.getRolePriviledges()) {
			if (domainObject.equals(priv.getDomainObject()) && priviledgeType.equals(priv.getPriviledgeTypeCode())) {
				priviledge = priv;
				break;
			}
		}

		if (priviledge == null) {
			priviledge = new RolePriviledge();
			priviledge.setName(getMessage(priviledgeType.getInlLabel()));
			priviledge.setRole(this.role);
			priviledge.setDomainObject(domainObject);
			priviledge.setPriviledgeTypeCode(priviledgeType);
			priviledge.setStyleClass("privNode");
		} else {
			priviledge.setName(getMessage(priviledgeType.getInlLabel()));
			priviledge.setStyleClass("privNode");
		}

		return priviledge;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public TreeNode getRolePriviledges() {
		return rolePriviledges;
	}

	public void setRolePriviledges(TreeNode rolePriviledges) {
		this.rolePriviledges = rolePriviledges;
	}

	public TreeNode[] getSelectedRolePriviledges() {
		return selectedRolePriviledges;
	}

	public void setSelectedRolePriviledges(TreeNode[] selectedRolePriviledges) {
		this.selectedRolePriviledges = selectedRolePriviledges;
	}

	public AdministrationObjectCache getAdministrationObjectCache() {
		return administrationObjectCache;
	}

	public void setAdministrationObjectCache(AdministrationObjectCache administrationObjectCache) {
		this.administrationObjectCache = administrationObjectCache;
	}
	
	@Override
	public DomainObjectType getDomainObjectType() {
		return DomainObjectType.ROLE;
	}
}
