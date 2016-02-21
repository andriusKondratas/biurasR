package ioffice.br.pageflow.administration.beans;

import java.io.Serializable;
import java.util.Iterator;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import ioffice.br.pageflow.common.beans.BasicBean;
import ioffice.br.persistance.enums.DomainObjectType;
import ioffice.br.persistance.enums.DomainType;
import ioffice.br.persistance.enums.PriviledgeType;

import org.primefaces.event.MenuActionEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuItem;
import org.primefaces.model.menu.MenuModel;
import org.springframework.beans.BeanUtils;

@ManagedBean(name = "menuMB")
@SessionScoped
public class MenuBean extends BasicBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String MENU_CHANGE_EVENT_BINDING = "#{menuMB.menuChange}";
	private static final String TAB_CHANGE_EVENT_BINDING = "#{menuMB.tabChange}";

	private boolean collapsed;
	private int tabIndex;

	private MenuModel menuModel;
	private MenuModel internalClassificationTabModel;
	private MenuModel actualClassificationTabModel;
	private MenuModel breadCrumbModel;

	public void loadModel() {
		this.breadCrumbModel = new DefaultMenuModel();
		this.breadCrumbModel.addElement(new DefaultMenuItem("HOME", null, "/pages/welcome.xhtml"));
		loadMenuModel();
		loadInternalClassificationTabModel();
		loadActualClassificationTabModel();
	}

	public void toggleLayout(ToggleEvent te) {
		// We have only one collapse=true layout unit so far
		// LayoutUnit panel = (LayoutUnit)te.getComponent();
		collapsed = !te.getVisibility().equals(Visibility.VISIBLE);
	}

	public void menuChange(MenuActionEvent e) {
		tabIndex = 0;
		updateBreadCrumbModel(e.getMenuItem(), MenuItemType.MENU);
		navigate(requestScope().getParameter("url"));
	}

	public void tabChange(MenuActionEvent e) {
		tabIndex = Integer.parseInt(requestScope().getParameter("index"));
		updateBreadCrumbModel(e.getMenuItem(), MenuItemType.TAB);
		navigate(requestScope().getParameter("url"));
	}

	public void navigateInternalClassificationTab() {
		MenuItem item = findFirstActiveTab(internalClassificationTabModel);
		if (item == null) {
			return;
		}
		updateBreadCrumbModel(item, MenuItemType.TAB);
		navigate(item.getParams().get("url").get(0));
	}

	public void navigateActualClassificationTab() {
		MenuItem item = findFirstActiveTab(actualClassificationTabModel);
		if (item == null) {
			return;
		}
		updateBreadCrumbModel(item, MenuItemType.TAB);
		navigate(item.getParams().get("url").get(0));
	}

	private MenuItem findFirstActiveTab(MenuModel model) {
		MenuItem activeiItem = null;
		for (MenuElement item : model.getElements()) {
			if (!((MenuItem) item).isDisabled()) {
				activeiItem = (MenuItem) item;
				tabIndex = Integer.parseInt(item.getId());
				break;
			}
		}
		return activeiItem;
	}

	private void loadMenuModel() {
		this.menuModel = new DefaultMenuModel();
		for (DomainType domainType : DomainType.values()) {
			if (domainType.getParentDomain() == null && hasAnyNestedAccess(domainType, PriviledgeType.VIEW)) {
				DefaultSubMenu subMenu = new DefaultSubMenu(getMessage(domainType.getInlLabel()), domainType.getIcon());
				subMenu.setId(domainType.getSeqNo().toString());

				for (DomainType domainTypeChild : domainType.getChildDomains()) {
					if (hasAnyAccess(domainTypeChild, PriviledgeType.VIEW)) {
						subMenu.addElement(createDefaultMenuItem(MenuItemType.MENU, getMessage(domainTypeChild.getInlLabel()), domainTypeChild.getSeqNo()
								.toString(), domainTypeChild.getUrl(), "customTableLink", false, domainTypeChild.getIcon()));
					}
				}

				for (DomainObjectType domainObjectType : DomainObjectType.values()) {
					if (domainObjectType.getDomain().equals(domainType) && !domainObjectType.isHidden() && hasAccess(domainObjectType, PriviledgeType.VIEW)) {
						subMenu.addElement(createDefaultMenuItem(MenuItemType.MENU, getMessage(domainObjectType.getInlLabel()), domainObjectType.getSeqNo()
								.toString(), domainObjectType.getUrl(), "customTableLink", false, domainObjectType.getIcon()));
					}
				}
				//subMenu.setExpanded(subMenu.getElementsCount() > 0);
				menuModel.addElement(subMenu);
			}
		}
	}

	private DefaultMenuItem createDefaultMenuItem(MenuItemType type, Object value, String id, String url, String styleClass, boolean disabled, String icon) {
		DefaultMenuItem it = new DefaultMenuItem(value, null, null);

		it.setId(id);
		it.setParam("index", id);
		it.setParam("url", url);
		it.setCommand(type.binding);
		it.setIcon(icon);
		//it.setAjax(true);
		//it.setDisabled(disabled);
		if(type.equals(MenuItemType.MENU)){
		 //it.setIcon("fa fa-fw fa-angle-double-right");
		 //it.setStyle("margin-left:10px");
		}
		if (styleClass != null) {
			//it.setStyleClass(styleClass);
		}
		return it;
	}

	private void loadInternalClassificationTabModel() {
		this.internalClassificationTabModel = new DefaultMenuModel();
		for (DomainObjectType domainObjectType : DomainObjectType.values()) {
			if (domainObjectType.getDomain().equals(DomainType.CLI_INT) && !domainObjectType.isHidden()) {
				internalClassificationTabModel.addElement(createDefaultMenuItem(MenuItemType.TAB, getMessage(domainObjectType.getInlLabel()), domainObjectType
						.getSeqNo().toString(), domainObjectType.getUrl(), null, !hasAccess(domainObjectType, PriviledgeType.VIEW), domainObjectType.getIcon()));
			}
		}
	}

	private void loadActualClassificationTabModel() {
		this.actualClassificationTabModel = new DefaultMenuModel();
		for (DomainObjectType domainObjectType : DomainObjectType.values()) {
			if (domainObjectType.getDomain().equals(DomainType.CLI_ACT) && !domainObjectType.isHidden()) {
				actualClassificationTabModel.addElement(createDefaultMenuItem(MenuItemType.TAB, getMessage(domainObjectType.getInlLabel()), domainObjectType
						.getSeqNo().toString(), domainObjectType.getUrl(), null, !hasAccess(domainObjectType, PriviledgeType.VIEW), domainObjectType.getIcon()));
			}
		}
	}

	private void updateBreadCrumbModel(MenuElement itemClicked, MenuItemType itemType) {
		if (itemType.equals(MenuItemType.MENU)) {
			removeBreadCrumbItems(MenuItemType.TAB, MenuItemType.MENU, MenuItemType.SUBMENU);
			for (MenuElement subMenuItem : menuModel.getElements()) {
				for (MenuElement item : ((DefaultSubMenu) subMenuItem).getElements()) {
					if (item.equals(itemClicked)) {
						// got it, here is our path
						addBreadCrumbItem(subMenuItem, itemType);
						addBreadCrumbItem(item, itemType);
						break;
					}
				}
			}
		} else if (itemType.equals(MenuItemType.TAB)) {
			removeBreadCrumbItems(MenuItemType.TAB);
			addBreadCrumbItem(itemClicked, itemType);
		} else {
			throw new IllegalArgumentException(itemType + " for breadcrumb is not supported");
		}
	}

	private void removeBreadCrumbItems(MenuItemType... types) {
		Iterator<MenuElement> iterator = breadCrumbModel.getElements().iterator();
		while (iterator.hasNext()) {
			DefaultMenuItem item = (DefaultMenuItem) iterator.next();
			for (MenuItemType t : types) {
				switch (t) {
				case TAB:
					if (item.getCommand() != null && item.getCommand().equals(TAB_CHANGE_EVENT_BINDING)) {
						iterator.remove();
					}
					break;
				case MENU:
					if (item.getCommand() != null && item.getCommand().equals(MENU_CHANGE_EVENT_BINDING)) {
						iterator.remove();
					}
					break;
				case SUBMENU:
					if (item.getCommand() == null && item.getUrl() == null) {
						iterator.remove();
					}
					break;
				default:
					break;
				}
			}
		}
	}

	private void addBreadCrumbItem(MenuElement item, MenuItemType itemType) {
		DefaultMenuItem bItem = new DefaultMenuItem();
		BeanUtils.copyProperties(item, bItem);
		if (item instanceof DefaultSubMenu) {
			bItem.setValue(((DefaultSubMenu) item).getLabel());
		}
		//bItem.setStyleClass("customNavLink");
		//bItem.setIcon(null);
		//bItem.setStyle(null);
		// Not visible at all - consider different style
		// bItem.setDisabled(bItem.getCommand() == null);
		breadCrumbModel.addElement(bItem);
	}

	public boolean isCollapsed() {
		return collapsed;
	}

	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}

	public MenuModel getMenuModel() {
		return menuModel;
	}

	public void setMenuModel(MenuModel menuModel) {
		this.menuModel = menuModel;
	}

	public MenuModel getBreadCrumbModel() {
		return breadCrumbModel;
	}

	public void setBreadCrumbModel(MenuModel breadCrumbModel) {
		this.breadCrumbModel = breadCrumbModel;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

	public MenuModel getInternalClassificationTabModel() {
		return internalClassificationTabModel;
	}

	public void setInternalClassificationTabModel(MenuModel internalClassificationTabModel) {
		this.internalClassificationTabModel = internalClassificationTabModel;
	}

	public MenuModel getActualClassificationTabModel() {
		return actualClassificationTabModel;
	}

	public void setActualClassificationTabModel(MenuModel actualClassificationTabModel) {
		this.actualClassificationTabModel = actualClassificationTabModel;
	}

	public enum MenuItemType {
		TAB(TAB_CHANGE_EVENT_BINDING), SUBMENU(null), MENU(MENU_CHANGE_EVENT_BINDING);

		private String binding;

		private MenuItemType(String binding) {
			this.binding = binding;

		}

		public String getBinding() {
			return binding;
		}
	}
}
