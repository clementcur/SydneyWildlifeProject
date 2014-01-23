package org.sydwildlife.service.reporting;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateMidnight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.sydwildlife.dao.user.UserDAO;
import org.sydwildlife.domain.User;
import org.sydwildlife.dto.VisitFrequency;

@Service
public class ReportingServiceImpl implements ReportingService {
	private static Logger LOG = LoggerFactory.getLogger(ReportingServiceImpl.class);
	
	private UserDAO userDAO;
	
	@Inject
	public ReportingServiceImpl(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	@Override
	public List<VisitFrequency> getVisitFrequency() {
		List<VisitFrequency> visitFrequencyList = new ArrayList<VisitFrequency>();
		List<User> userList = userDAO.loadAll();
		Map<Date, Integer> freqMap = new Hashtable<Date, Integer>();
		for (User user : userList) {
			if (user.valid()){
				DateMidnight dm = new DateMidnight(user.getVisitDate());
				int i = (freqMap.get(dm.toDate()) == null? 0: freqMap.get(dm.toDate()));
				freqMap.put(dm.toDate(), ++i);			
			}
		}
		Set<Date> keys = freqMap.keySet();
		for (Date date : keys) {
			VisitFrequency v = new VisitFrequency();
			v.setVisitDate(date);
			int i = freqMap.get(date);
			v.setNumberOfVisits(i);
			LOG.debug("VisitDate: " + date + ", Freq: " + i);
			visitFrequencyList.add(v);
		}
		LOG.debug("Found " + visitFrequencyList.size() + " visits!");
		return visitFrequencyList;
	}

}
