class Quiz {
	public:
		void set_question_1(string new_question);
		void set_question_2(string new_question);
		void set_question_3(string new_question);
		void set_answer_1(string new_answer);
		void set_answer_2(string new_answer);
		void set_answer_3(string new_answer);
		double get_question_score_1() const;
		double get_question_score_2() const;
		double get_question_score_3() const;
		
	private:
		string question_1;
		string answer_1;
		string question_2;
		string answer_2;
		string question_3;
		string answer_3;
		double ask_question(string question, string answer) const; //helper method
};

void Quiz::set_question_1(string new_question) {
     question_1 = new_question;
}
void Quiz::set_answer_1(string new_answer) {
     answer_1 = new_answer;
}
void Quiz::set_question_2(string new_question) {
     question_2 = new_question;
}
void Quiz::set_answer_2(string new_answer) {
     answer_2 = new_answer;
}
void Quiz::set_question_3(string new_question) {
     question_3 = new_question;
}
void Quiz::set_answer_3(string new_answer) {
     answer_3 = new_answer;
}

double Quiz::get_question_score_1() const{
	return ask_question(question_1, answer_1);
}

double Quiz::get_question_score_2() const{
	return ask_question(question_2, answer_2);
}

double Quiz::get_question_score_3() const{
	return ask_question(question_3, answer_3);
}

double Quiz::ask_question(string question, string correct_answer) const{
	string student_answer;
    double score, time;
	clock_t timer_begin, timer_end, clocks_elapsed; //used for student response time
	
	cout << question;
	timer_begin = clock(); //start timer
    cin >> student_answer;
    timer_end = clock(); //stop timer
    
    clocks_elapsed = timer_end - timer_begin;
    time = clocks_elapsed / static_cast<double>(CLOCKS_PER_SEC); //convert time to seconds
	
	if ((student_answer == correct_answer)&&(time <= 10)) {
		score = 100;
	}
	else if ((student_answer == correct_answer)&&(time <= 20)) {
		score = 90;
	}
	else if ((student_answer == correct_answer)&&(time <= 30)) {
		score = 80;
	}
	else if ((student_answer == correct_answer)&&(time <= 40)) {
		score = 70;
	}
	else if ((student_answer == correct_answer)&&(time <= 60)) {
		score = 60;
	}
	else {
		score = 0;
	}
	
	return score;
}
