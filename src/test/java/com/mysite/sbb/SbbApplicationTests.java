package com.mysite.sbb;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.QuestionRepository;
import com.mysite.sbb.question.QuestionService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	private QuestionService questionService;

	@DisplayName("Question Entity Check")
	@Test
	void testQuestion() {
		Question q1 = Question.builder()
				.subject("sbb가 무엇인가요?")
				.content("sbb에 대해서 알고 싶습니다.")
				.createDate(LocalDateTime.now())
				.build();
		questionRepository.save(q1);

		Question q2 = Question.builder()
				.subject("스프링부트 모델 질문입니다.")
				.content("id는 자동으로 생성되나요?")
				.createDate(LocalDateTime.now())
				.build();
		questionRepository.save(q2);
	}

	@DisplayName("Question findAll")
	@Test
	void testQuestionFindAll() {
		List<Question> all = questionRepository.findAll();
		assertEquals(2, all.size());

		Question q = all.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
	}

	@DisplayName("Question findById")
	@Test
	void testQuestionFinById() {
		Optional<Question> oq = questionRepository.findById(1L);
		if (oq.isPresent()) {
			Question q = oq.get();
			assertEquals("sbb에 대해서 알고 싶습니다.", q.getContent());
		}
	}

	@DisplayName("Question findBySubject")
	@Test
	void testQuestionFindBySubject() {
		Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
		assertEquals(1, q.getId());
	}

	@DisplayName("Question findBySubjectAndContent")
	@Test
	void testQuestionFindBySubjectAnd() {
		Question q = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
		System.out.println(q);
		assertEquals(1L, q.getId());
	}

	@DisplayName("Question findBySubjectLike")
	@Test
	void testQuestionFindBySubjectList() {
		List<Question> qList = questionRepository.findBySubjectLike("sbb%");
		Question q = qList.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
	}

//	@DisplayName("Question update")
//	@Test
//	void testQuestionUpdate() {
//		assertEquals(2, questionRepository.count());
//		Optional<Question> oq = questionRepository.findById(1L);
//		assertTrue(oq.isPresent());
//		Question q = oq.get();
//		questionRepository.delete(q);
//		assertEquals(1, questionRepository.count());
//	}

	@DisplayName("Answer Insert")
	@Test
	void testAnswerInsert() {
		Optional<Question> oq = this.questionRepository.findById(2L);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		Answer a = Answer.builder()
				.id(1L)
				.content("네 자동으로 생성됩니다.")
				.question(q)
				.createDate(LocalDateTime.now())
				.build();
		answerRepository.save(a);
	}

	@DisplayName("Answer findById")
	@Test
	void testAnswerFindById() {
		Optional<Answer> oa = answerRepository.findById(1L);
		assertTrue(oa.isPresent());
		Answer a = oa.get();
		assertEquals(2, a.getQuestion().getId());
	}

	@DisplayName("Answer & Question")
	@Transactional
	@Test
	void testAnswer_Question() {
		Optional<Question> oq = this.questionRepository.findById(2L);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		List<Answer> answerList = q.getAnswerList();

		assertEquals(1, answerList.size());
		assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
	}

	@DisplayName("300 개의 테스트 케이스 생성")
	@Test
	void testCreateCase() {
		for (int i = 1; i <= 300; i++) {
			String subject = String.format("테스트 데이터입니다:[%03d]", i);
			String content = "내용무";
			questionService.create(subject, content, null);
		}
	}
}
