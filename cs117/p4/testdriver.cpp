#include <cstdlib>
#include <iostream>

using namespace std;

#include "student.h"
#include "quiz.h"
#include "quizproctor.h"

int main() {
    QuizProctor quiz;
    string student_name;
    
    cout << "------------------------------------------------------\n";
    cout << "I hope you're ready for a quiz!\n";
    cout << "You will be asked 3 true or false questions.\n";
    cout << "You will be graded on correctness and speed.\n";
    cout << "Speed Scale:\n";
    cout << "0 - 10 seconds: 100%\n";
    cout << "11 - 20 seconds: 90%\n";
    cout << "21 - 30 seconds: 80%\n";
    cout << "31 - 40 seconds: 70%\n";
    cout << "41 - 60 seconds: 60%\n";
    cout << "You will not get any points after a minute.\n";
    cout << "Good Luck!!!\n";
    cout << "------------------------------------------------------\n\n";
    
    string question_1 = "Birds have wings [true or false]: ";
    string answer_1 = "true";
    string question_2 = "Dogs have only 2 legs [true or false]: ";
    string answer_2 = "false";
    string question_3 = "Cats chase mice [true or false]: ";
    string answer_3 = "true";
    
    cout << "Enter student name: ";
    cin >> student_name;
        
    quiz.proctor_quiz(student_name,question_1,answer_1,question_2,answer_2,question_3,answer_3);

    return 0;
}
