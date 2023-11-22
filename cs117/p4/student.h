class Student {
	public:
		Student();
        void set_name(string new_name);
		string get_name() const;
		void add_score(double score);
		void display_report() const;
		
	private:
        string name;
		double score_total;
		int num_scores;
		double get_ave_score(double total, int num_scores) const;
		char get_grade(double ave_score) const;
};

Student::Student() {
    score_total = 0.0;
    num_scores = 0;
}

void Student::set_name(string new_name) {
	name = new_name;
}

string Student::get_name() const{
	return name;
}

void Student::add_score(double score) {
	score_total += score;
	num_scores++;
}

void Student::display_report() const{
	cout << "\nStudent Name: " << name;
	cout << "\nTotal Score: " << score_total;
	cout << "\nAverage Score: " << get_ave_score(score_total, num_scores);
	cout << "\nGrade: " << get_grade(get_ave_score(score_total, num_scores));
}

double Student::get_ave_score(double total, int num_scores) const{
	return (total / num_scores);
}

char Student::get_grade(double ave_score) const{
	if (ave_score >= 90) {
		return 'A';
	}
	else if (ave_score >= 80) {
		return 'B';
	}
	else if (ave_score >= 70) {
		return 'C';
	}
	else if (ave_score >= 60) {
		return 'D';
	}
	else {
		return 'F';
	}
}
