package com.example.questify

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.toColor
import com.example.questify.R


data class Question(
    val text: String,
    val options: List<String>,
    val correctAnswer: Int
)

class QuizActivity : AppCompatActivity() {

    lateinit var topicName : String
    private lateinit var questionText: TextView
    private lateinit var optionButtons: List<Button>
    private lateinit var nextButton: Button
    private lateinit var optionText: List<TextView>
    private lateinit var optionButtonIcons: List<ImageView>

    private var selectedOption = -1
    private var hasAnsweredCorrectly = false

    private var currentQuestionIndex = 0
    private var score = 0

    private val scienceQuestions = listOf(
        Question("What is the chemical symbol for water?", listOf("H2O", "CO2", "O2", "NaCl"), 0),
        Question("Which planet is known as the Red Planet?", listOf("Earth", "Mars", "Jupiter", "Venus"), 1),
        Question("What is the powerhouse of the cell?", listOf("Nucleus", "Mitochondria", "Endoplasmic reticulum", "Golgi apparatus"), 1),
        Question("Which gas do plants absorb during photosynthesis?", listOf("Oxygen", "Carbon dioxide", "Nitrogen", "Hydrogen"), 1),
        Question("What is the largest mammal on Earth?", listOf("Elephant", "Blue whale", "Giraffe", "Hippopotamus"), 1),
        Question("What is the process by which plants make their own food?", listOf("Respiration", "Transpiration", "Photosynthesis", "Fermentation"), 2),
        Question("Which scientist is famous for his theory of relativity?", listOf("Isaac Newton", "Galileo Galilei", "Albert Einstein", "Stephen Hawking"), 2),
        Question("What is the chemical symbol for gold?", listOf("Au", "Ag", "Fe", "Cu"), 0),
        Question("Which gas makes up the majority of Earth's atmosphere?", listOf("Oxygen", "Nitrogen", "Carbon dioxide", "Argon"), 1),
        Question("What is the smallest bone in the human body?", listOf("Femur", "Stapes", "Radius", "Ulna"), 1)
    )

    private val historyQuestions = listOf(
        Question("Who was the first Emperor of the Maurya Dynasty in ancient India?", listOf("Chandragupta Maurya", "Ashoka the Great", "Bindusara", "Kanishka"), 0),
        Question("In which year did Christopher Columbus first reach the Americas?", listOf("1492", "1521", "1607", "1776"), 0),
        Question("Who was the first President of the United States?", listOf("John Adams", "Thomas Jefferson", "George Washington", "Abraham Lincoln"), 2),
        Question("During which war did the Battle of Gettysburg take place?", listOf("American Revolution", "Civil War", "World War I", "World War II"), 1),
        Question("Who was the Mughal Emperor who built the Taj Mahal?", listOf("Humayun", "Akbar", "Shah Jahan", "Aurangzeb"), 2),
        Question("Which ancient civilization is known for the construction of the Great Wall?", listOf("Egyptian", "Roman", "Chinese", "Greek"), 2),
        Question("Who was known as \"Iron Man\" of India", listOf("Indira Gandhi", "Jawaharlal Nehru", "Sardar Patel", "Rajiv Gandhi"), 2),
        Question("In which year did the French Revolution begin?", listOf("1776", "1789", "1804", "1830"), 1),
        Question("Who was the leader of the Indian independence movement known as Netaji ?", listOf("Subhas Chandra Bose", "Jawaharlal Nehru", "Mahatma Gandhi", "Bhagat Singh"), 0),
        Question("Which ancient city is known as the 'City of David'?", listOf("Athens", "Rome", "Jerusalem", "Babylon"), 2)
    )

    private val genKQuestions = listOf(
        Question("Which river is the longest in the world?", listOf("Nile", "Amazon", "Yangtze", "Mississippi"), 0),
        Question("What is the capital city of Australia?", listOf("Sydney", "Canberra", "Melbourne", "Brisbane"), 1),
        Question("Which country is known as the 'Land of the Rising Sun'?", listOf("China", "Japan", "South Korea", "Vietnam"), 1),
        Question("Who wrote the play 'Romeo and Juliet'?", listOf("Charles Dickens", "William Shakespeare", "Jane Austen", "Leo Tolstoy"), 1),
        Question("What is the currency of Brazil?", listOf("Peso", "Real", "Rupee", "Dollar"), 1),
        Question("Which ocean is the largest on Earth?", listOf("Atlantic Ocean", "Indian Ocean", "Arctic Ocean", "Pacific Ocean"), 3),
        Question("Which famous landmark is located in the center of Paris, France?", listOf("Eiffel Tower", "Big Ben", "Statue of Liberty", "Colosseum"), 0),
        Question("What is the world's largest desert by area?", listOf("Sahara Desert", "Arabian Desert", "Gobi Desert", "Antarctica"), 3),
        Question("Who is the author of 'To Kill a Mockingbird'?", listOf("J.K. Rowling", "Harper Lee", "George Orwell", "Mark Twain"), 1),
        Question("In which year did the Berlin Wall fall, marking the end of the Cold War?", listOf("1989", "1991", "1985", "1993"), 1)
    )

    private val techQuestions = listOf(
        Question("What is the popular programming language developed by Google?", listOf("Java", "Python", "C#", "Go"), 3),
        Question("Which company developed the Android operating system?", listOf("Apple", "Microsoft", "Google", "Samsung"), 2),
        Question("What does CPU stand for in computer terms?", listOf("Central Processing Unit", "Computer Processing Unit", "Central Processor Unit", "Central Peripheral Unit"), 0),
        Question("Which social media platform is known for its character limit in posts?", listOf("Instagram", "Facebook", "Twitter", "LinkedIn"), 2),
        Question("What is the purpose of a firewall in computer security?", listOf("Virus Removal", "Unauthorized Access Prevention", "Data Encryption", "File Backup"), 1),
        Question("Which programming language is commonly used for web development and design?", listOf("Swift", "HTML", "Ruby", "Objective-C"), 1),
        Question("What is the term for the process of converting source code into machine code?", listOf("Compiling", "Debugging", "Interpreting", "Linking"), 0),
        Question("Which tech company is associated with the slogan 'Think Different'?", listOf("Microsoft", "IBM", "Apple", "Google"), 2),
        Question("What does the acronym 'URL' stand for in the context of the internet?", listOf("Uniform Resource Locator", "Universal Resource Locator", "Unified Resource Locator", "Ultimate Resource Locator"), 0),
        Question("Which of the following is not a type of computer memory?", listOf("RAM", "ROM", "CPU", "Cache"), 2)
    )


    private lateinit var questions:List<Question>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        topicName = intent.getStringExtra("topic") ?:"science"

        when (topicName){
            "science" -> {questions=scienceQuestions}
            "history" -> {questions=historyQuestions}
            "genK" -> {questions=genKQuestions}
            "tech" -> {questions=techQuestions}
        }

        //questions=scienceQuestions


        // Initialize views
        questionText = findViewById(R.id.questionText)
        optionButtons = listOf(
            findViewById(R.id.optABtn),
            findViewById(R.id.optBBtn),
            findViewById(R.id.optCBtn),
            findViewById(R.id.optDBtn)
        )
        optionText = listOf(
            findViewById(R.id.optionAText),
            findViewById(R.id.optionBText),
            findViewById(R.id.optionCText),
            findViewById(R.id.optionDText)
        )

        optionButtonIcons = listOf(
            findViewById(R.id.ABtnIcon),
            findViewById(R.id.BBtnIcon),
            findViewById(R.id.CBtnIcon),
            findViewById(R.id.DBtnIcon)
        )

        nextButton = findViewById(R.id.nextBtn)

        // Display the first question
        displayQuestion(currentQuestionIndex)

        nextButton.setOnClickListener {
            checkAnswer()  // Removed the argument (i)
            currentQuestionIndex++
            if (currentQuestionIndex < questions.size) {
                displayQuestion(currentQuestionIndex)
            } else {
                // All questions answered, show results
                showResults()
            }
        }

    }

    private fun displayQuestion(index: Int) {
        hasAnsweredCorrectly = false
        changeIcon(-1)
        val question = questions[index]
        questionText.text = question.text
        optionText.forEachIndexed { i, textView ->
            textView.text = question.options[i]
            optionButtons[i].setOnClickListener { checkAnswer(i) }
        }
    }


    private fun checkAnswer(newSelectedOption: Int = -1) {
        if (selectedOption != newSelectedOption) {
            selectedOption = newSelectedOption
            val question = questions[currentQuestionIndex]
            val correctAnswer = question.correctAnswer

            if (selectedOption == correctAnswer && !hasAnsweredCorrectly) {
                score++
                hasAnsweredCorrectly = true  // Set flag to prevent multiple score increments
                changeIcon(selectedOption)
                //optionButtons[selectedOption].setBackgroundColor(selectedOptionColor)
                Log.d("QuizActivity", "Correct answer!  hasanc: $hasAnsweredCorrectly . score: $score")
            }

            else {
                // Provide visual feedback for incorrect answer (e.g., shake option)
                changeIcon(selectedOption)
                Log.d("QuizActivity", "Incorrect answer. Correct answer was option ${correctAnswer + 1}. hasanc: $hasAnsweredCorrectly . score: $score")
            }
        }
    }

    private fun changeIcon(currentSelectedOption: Int=-1){
        for (i in 0..3){
            if(i==currentSelectedOption){
                optionButtonIcons[i].setImageResource(R.drawable.checkfilled)
            }
            else{
                optionButtonIcons[i].setImageResource((R.drawable.checkempty))
            }
        }
    }

    private fun showResults() {
        // Start a new activity to display the final score
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("score", score)
        intent.putExtra("totalQuestions", questions.size)
        startActivity(intent)
    }
}