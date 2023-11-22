class QuizProctor {
	public:
		QuizProctor();
        void proctor_quiz(string new_name,string question1,string answer1,string question2,string answer2,string question3,string answer3);
	
    private:
		Quiz quiz_1;
		Student student_1;
};

QuizProctor::QuizProctor() {
        
}

void QuizProctor::proctor_quiz(string new_name,string question1,string answer1,string question2,string answer2,string question3,string answer3) {
	student_1.set_name(new_name);
	quiz_1.set_question_1(question1);
	quiz_1.set_answer_1(answer1);
	quiz_1.set_question_2(question2);
	quiz_1.set_answer_2(answer2);
	quiz_1.set_question_3(question3);
	quiz_1.set_answer_3(answer3);
	
	student_1.add_score(quiz_1.get_question_score_1());
	student_1.add_score(quiz_1.get_question_score_2());
	student_1.add_score(quiz_1.get_question_score_3());
    
	student_1.display_report();
}
