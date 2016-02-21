package ioffice.br.persistance.service.testdata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ioffice.br.persistance.enums.ActivityType;
import ioffice.br.persistance.enums.PersonState;
import ioffice.br.persistance.enums.RegionType;
import ioffice.br.persistance.model.classification.Activity;
import ioffice.br.persistance.model.classification.Person;
import ioffice.br.persistance.model.classification.Region;
import ioffice.br.persistance.service.classification.ActivityService;
import ioffice.br.persistance.service.classification.PersonService;
import ioffice.br.persistance.service.classification.RegionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("PersonTestDataService")
@Transactional(readOnly = false)
@Scope("singleton")
public class PersonTestDataService {

	@Autowired
	private PersonService personService;

	@Autowired
	private RegionService regionService;

	@Autowired
	private ActivityService activityService;

	public String createPersons() {
		try {
			createPerson("38203050985", "Abraomas", PersonState.ACTIVE, "Pusyno", "51", "34361", "Kaunas", "Šilainiai", "+37065026513", null,
					"abrlink@atea.lt", "Fizinis asmuo", "Linkolnas", null, null, null, null, null, ActivityType.CAPTAIN, ActivityType.VESSEL_OWNER,
					RegionType.BALTIC, RegionType.COAST, RegionType.ATLANTIC);
			
			createPerson("38303050985", "Vejas", PersonState.ACTIVE, "Pylimo"," 52", "54361", "Šiauliai", "Priemiestis", "+37065026813", null,
					"abrlink@atea.lt", "Fizinis asmuo", "Šaltas", null, null, null, null, null, ActivityType.OPERATOR, ActivityType.INSPECTOR,
					RegionType.BALTIC, RegionType.COAST);

			createPerson("38403050985", "Menulis", PersonState.NOT_ACTIVE, "Savanorių", "45","74361", "Kaunas", "Domeikava", "+37066026813", null,
					"abrlink@atea.lt", "Fizinis asmuo", "Pilnatis", null, null, null, null, null, ActivityType.INSPECTOR, ActivityType.VESSEL_OWNER,
					RegionType.BALTIC);

			createPerson("38503050985", "Saule", PersonState.ACTIVE, "Utenos", "87", "24361", "Anyk�?čiai", "Kaimas", "+37065026813", null,
					"abrlink@atea.lt", "Fizinis asmuo", "Kaitri", null, null, null, null, null, ActivityType.CAPTAIN, ActivityType.VESSEL_OWNER,
					RegionType.BALTIC, RegionType.COAST, RegionType.ATLANTIC);

			return "Person test data created";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	@SuppressWarnings("rawtypes")
	private void createPerson(String code, String name, PersonState state, String street, String building, String postalCode, String city,
			String district, String phoneNumber1, String phoneNumber2, String email, String comments, String surname, String secondName, Date birthDate,
			Date registrationDate, Date unRegistrationDate, String webSite, Enum... types) throws Exception {

		Person person = new Person();

		person.setIndividualCode(code);
		person.setIndividualName(name);
		person.setState(state);
		person.setIndividualStreet(street);
		person.setIndividualBuilding(building);
		person.setIndividualPostalCode(postalCode);
		person.setIndividualCity(city);
		person.setIndividualDistrict(district);
		person.setIndividualPhoneNumber1(phoneNumber1);
		person.setIndividualPhoneNumber2(phoneNumber2);
		person.setIndividualEmail(email);
		person.setComments(comments);
		person.setIndividualSurname(surname);
		person.setIndividualSecondName(secondName);
		person.setIndividualBirthDate(birthDate);
		person.setLegalRegistrationDate(registrationDate);
		person.setLegalUnRegistrationDate(unRegistrationDate);
		person.setLegalWebSite(webSite);

		List<Activity> activityList = new ArrayList<Activity>();
		List<Region> regionList = new ArrayList<Region>();

		for (Enum e : types) {
			if (e instanceof ActivityType) {
				Activity act = activityService.findByType((ActivityType) e);
				if (act == null) {
					throw new IllegalArgumentException("Can't find activity with code:" + ((ActivityType) e).name());
				}
				activityList.add(act);
			} else if (e instanceof RegionType) {
				Region reg = regionService.findByType((RegionType) e);
				if (reg == null) {
					throw new IllegalArgumentException("Can't find region with code:" + ((RegionType) e).name());
				}
				regionList.add(reg);
			} else {
				throw new IllegalArgumentException("Unknown type with code:" + e.name());
			}
		}

		person.setActivities(activityList);
		person.setRegions(regionList);

		try {
			//personService.saveOrUpdate(person);
		} catch (Exception e) {
			throw new Exception("Unable to add person with code: " + code + "<br>");
		}
	}

	public String createRegions() {
		try {
			for (RegionType regionType : RegionType.values()) {
				createRegion(regionType);
			}

			return "Regions test data created";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	private void createRegion(RegionType regionType) throws Exception {
		Region region = new Region();
		region.setCode(regionType);
		try {
			regionService.saveOrUpdate(region);
		} catch (Exception e) {
			throw new Exception(String.format("Unable to create region with code: %s", regionType.name()), e);
		}
	}

	public String createActivities() {
		try {
			for (ActivityType activityType : ActivityType.values()) {
				createActivity(activityType);
			}

			return "Activities test data created";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	private void createActivity(ActivityType activityType) throws Exception {
		Activity activity = new Activity();
		activity.setCode(activityType);
		try {
			activityService.saveOrUpdate(activity);
		} catch (Exception e) {
			throw new Exception(String.format("Unable to create activity with code: %s", activityType.name()), e);
		}
	}
}
