package foo.repository;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.joda.time.LocalDate;
import org.springframework.data.jpa.domain.Specification;

import foo.domain.User;

public class UserSpecs {
	public static Specification<User> bornBefore(final LocalDate date) {

		return new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Path<Date> birthdate = root.<Date> get("birthdate");
				Predicate userBornBefore = cb.lessThan(birthdate, date.toDateMidnight().toDate());
				return userBornBefore;
			}
		};
	}
}
