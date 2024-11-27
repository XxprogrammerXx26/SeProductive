package com.myprimer.seproductive.Modelo


import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Question(val question: String, val options: List<String>, val correctAnswer: String)

class CulturaGeneralViewModel : ViewModel() {
    private val _currentQuestion = MutableStateFlow<Question?>(null)
    val currentQuestion: StateFlow<Question?> = _currentQuestion

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

    private val _isGameOver = MutableStateFlow(false)
    val isGameOver: StateFlow<Boolean> = _isGameOver

    private val questions = listOf(
        Question("¿Cuál es la capital de Francia?", listOf("Madrid", "París", "Roma", "Londres"), "París"),
        Question("¿Cuál es el río más largo del mundo?", listOf("Amazonas", "Nilo", "Yangtsé", "Misisipi"), "Amazonas"),
        Question("¿En qué año llegó Cristóbal Colón a América?", listOf("1492", "1500", "1485", "1510"), "1492"),
        Question("¿Quién pintó la Mona Lisa?", listOf("Vincent van Gogh", "Pablo Picasso", "Leonardo da Vinci", "Claude Monet"), "Leonardo da Vinci"),
        Question("¿Qué planeta es conocido como el Planeta Rojo?", listOf("Marte", "Venus", "Saturno", "Júpiter"), "Marte"),
        Question("¿Cuál es el símbolo químico del oro?", listOf("Au", "Ag", "Pb", "Fe"), "Au"),
        Question("¿En qué continente se encuentra el desierto del Sahara?", listOf("Asia", "África", "América", "Oceanía"), "África"),
        Question("¿Quién escribió 'Don Quijote de la Mancha'?", listOf("Gabriel García Márquez", "Miguel de Cervantes", "Federico García Lorca", "Mario Vargas Llosa"), "Miguel de Cervantes"),
        Question("¿Cuántos continentes hay en la Tierra?", listOf("5", "6", "7", "8"), "7"),
        Question("¿Cuál es el océano más grande del mundo?", listOf("Atlántico", "Índico", "Pacífico", "Ártico"), "Pacífico"),
        Question("¿Quién fue el primer hombre en pisar la luna?", listOf("Buzz Aldrin", "Neil Armstrong", "Yuri Gagarin", "Michael Collins"), "Neil Armstrong"),
        Question("¿En qué país nació el pintor Pablo Picasso?", listOf("Italia", "Francia", "España", "Portugal"), "España"),
        Question("¿Cuál es la capital de Japón?", listOf("Seúl", "Beijing", "Tokio", "Hanoi"), "Tokio"),
        Question("¿Cuál es el animal terrestre más grande?", listOf("Elefante africano", "Rhinoceros", "Jirafa", "Oso polar"), "Elefante africano"),
        Question("¿Qué elemento químico tiene el número atómico 1?", listOf("Helio", "Hidrógeno", "Oxígeno", "Carbono"), "Hidrógeno"),
        Question("¿Qué fecha marca el inicio de la Segunda Guerra Mundial?", listOf("1914", "1939", "1941", "1945"), "1939"),
        Question("¿Cuántos huesos tiene el cuerpo humano adulto?", listOf("206", "208", "210", "198"), "206"),
        Question("¿En qué país se encuentra la Torre Eiffel?", listOf("Italia", "Francia", "Reino Unido", "España"), "Francia"),
        Question("¿Quién fue el autor de la teoría de la relatividad?", listOf("Isaac Newton", "Galileo Galilei", "Albert Einstein", "Niels Bohr"), "Albert Einstein"),
        Question("¿Cuál es el país más grande del mundo?", listOf("China", "Canadá", "Rusia", "Brasil"), "Rusia"),
        Question("¿Cuál es la moneda de Japón?", listOf("Yen", "Won", "Peso", "Dólar"), "Yen"),
        Question("¿En qué año se firmó la Declaración de Independencia de los Estados Unidos?", listOf("1776", "1783", "1792", "1801"), "1776")

        // Agrega más preguntas aquí
    )

    private var currentQuestionIndex = 0

    init {
        loadNextQuestion()
    }

    fun submitAnswer(answer: String) {
        if (_currentQuestion.value?.correctAnswer == answer) {
            _score.value += 1
        }
        currentQuestionIndex += 1
        if (currentQuestionIndex >= questions.size) {
            _isGameOver.value = true
        } else {
            loadNextQuestion()
        }
    }

    fun restartGame() {
        _score.value = 0
        _isGameOver.value = false
        currentQuestionIndex = 0
        loadNextQuestion()
    }

    private fun loadNextQuestion() {
        _currentQuestion.value = questions.getOrNull(currentQuestionIndex)
    }
}
