<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Переводчик слов</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #f0f0f0;
        }
        .translator-container {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            margin-bottom: 20px;
            text-align: center;
        }
        form {
            display: flex;
            flex-direction: column;
            gap: 10px;
        }
        select, textarea {
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        button {
            padding: 10px;
            font-size: 16px;
            color: white;
            background-color: #007BFF;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #0056b3;
        }
    </style>
</head>

<body>
<div class="translator-container">
    <h1>Переводчик</h1>
    <form id="translator-form">
        <label for="from-language">С какого языка:</label>
        <textarea id="from-language" name="from-language" rows="1" placeholder="Введите язык здесь..."></textarea>

        <label for="to-language">На какой язык:</label>
        <textarea id="to-language" name="to-language" rows="1" placeholder="Введите язык здесь..."></textarea>

        <label for="sentence">Предложение для перевода:</label>
        <textarea id="sentence" name="sentence" rows="4" placeholder="Введите текст здесь..."></textarea>

        <button type="submit">Перевести</button>
    </form>
</div>

<script>
    document.getElementById('translator-form').addEventListener('submit', function(event) {
        event.preventDefault();
        const fromLanguage = document.getElementById('from-language').value;
        const toLanguage = document.getElementById('to-language').value;
        const sentence = document.getElementById('sentence').value;

        if (fromLanguage && toLanguage && sentence) {
            fetch('/translate', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        sourceLanguage: fromLanguage,
                        targetLanguage: toLanguage,
                        sentence: sentence
                    })
                })
                .then(response => response.json())
                .then(data => {
                    alert(data.translation);
                })
                .catch(error => {
                    console.error('Ошибка:', error);
                    alert('http 400 Ошибка доступа к ресурсу перевода');
                });
            } else if (!fromLanguage && toLanguage && sentence) {
                alert('http 400 Не найден язык исходного сообщения');
            } else if (fromLanguage && !toLanguage && sentence) {
                alert(`http 400 Не найден язык итогового сообщения`);
            } else {
                alert(`http 400 Не найдено выражение для перевода`);
            }
        });
</script>
</body>
</html>
