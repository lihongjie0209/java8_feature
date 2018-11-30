package cn.lihongjie.lambda.localdate;

import org.junit.Test;
import org.slf4j.Logger;

import java.time.*;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.util.GregorianCalendar;
import java.util.Locale;

import static java.time.temporal.ChronoField.*;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author 982264618@qq.com
 */
public class NewDateTimeTest {

	private static Logger logger = getLogger(NewDateTimeTest.class);


	@Test
	public void testNewDateTimeType() throws Exception {


		LocalDate localDate = LocalDate.now();

		logger.info("localDate : " + localDate.toString());


		LocalTime localTime = LocalTime.now();
		logger.info("localTime : " + localTime.toString());

		LocalDateTime localDateTime = LocalDateTime.now();

		logger.info("localDateTime : " + localDateTime.toString());


		OffsetTime offsetTime = OffsetTime.now();

		logger.info("offsetTime : " + offsetTime.toString());


		// 带偏移量的时间
		OffsetDateTime offsetDateTime = OffsetDateTime.now();

		logger.info("offsetDateTime : " + offsetDateTime.toString());


		// 带时区的时间
		ZonedDateTime zonedDateTime = ZonedDateTime.now();

		logger.info("zonedDateTime : " + zonedDateTime.toString());


		// 计算机中的毫秒, 绝对计时
		Instant instant = Instant.now();

		logger.info("instant : " + instant.toString());


		Year year = Year.now();
		logger.info("year : " + year.toString());

		YearMonth yearMonth = YearMonth.now();
		logger.info("yearMonth : " + yearMonth.toString());


		MonthDay monthDay = MonthDay.now();

		logger.info("monthDay : " + monthDay.toString());


	}


	@Test
	public void testDateTimeFiled() throws Exception {


		ZonedDateTime now = ZonedDateTime.now();

		now.getDayOfMonth();
		now.getDayOfWeek();
		now.getDayOfYear();
		now.getHour();
		now.getMinute();
		now.getSecond();
		now.getMonth();
		now.getMonthValue();
		now.getNano();
		now.getOffset();
		now.getYear();


	}


	@Test
	public void testDateTimeArithmetic() throws Exception {


		ZonedDateTime now = ZonedDateTime.now();


		// 不可变对象, 每次都返回一个新的实例
		now.plusNanos(1)
				.plusSeconds(1)
				.plusMinutes(1)
				.plusHours(1)
				.plusDays(1)
				.plusWeeks(1)
				.plusMonths(1)
				.plusYears(1);


		now.minusNanos(1)
				.minusSeconds(1)
				.minusMinutes(1)
				.minusHours(1)
				.minusDays(1)
				.minusWeeks(1)
				.minusMonths(1)
				.minusYears(1);


	}


	@Test
	public void testChronoLocalDate() throws Exception {

		Locale locale = Locale.getDefault();


		// 通用的日历框架, localdate 是在ISO标准上进行的, 底层实现就是这个
		Chronology chronology = Chronology.ofLocale(locale);

		ChronoLocalDate date = chronology.dateNow();
		logger.info(date.toString());
		ChronoZonedDateTime<? extends ChronoLocalDate> chronoZonedDateTime = chronology.zonedDateTime(Instant.now(), ZoneId.systemDefault());
		logger.info("chronoZonedDateTime: " + chronoZonedDateTime.toString());

	}


	@Test
	public void testDateTimeFormatter() throws Exception {


		DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
				.appendValue(HOUR_OF_DAY, 1, 2, SignStyle.NEVER)
				.optionalStart()
				.appendLiteral(":")
				.appendValue(MINUTE_OF_HOUR, 2)
				.optionalStart()
				.appendLiteral(":")
				.appendValue(SECOND_OF_MINUTE, 2)
				.optionalEnd()
				.optionalEnd()
				.parseDefaulting(MINUTE_OF_HOUR, 1)
				.parseDefaulting(SECOND_OF_MINUTE, 0)
				.toFormatter();


		LocalTime.parse("9", dateTimeFormatter);
		LocalTime.parse("09:05", dateTimeFormatter);
		LocalTime.parse("09:30:59", dateTimeFormatter);
		LocalTime.parse("23:00", dateTimeFormatter);


	}


	@Test
	public void testConvertToZonedDateTime() throws Exception {

		Instant instant = Instant.now();

		ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());

		GregorianCalendar calendar = new GregorianCalendar();

		ZonedDateTime zonedDateTime1 = calendar.toZonedDateTime();




	}


	@Test
	public void testDuration() throws Exception {


		Duration duration = Duration.ofHours(1);


	}
}
