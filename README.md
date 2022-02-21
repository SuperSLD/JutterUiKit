# Jutter UI Kit
![](https://img.shields.io/github/stars/SuperSLD/JutterUiKit) ![](https://img.shields.io/github/forks/SuperSLD/JutterUiKit)

---

**Последняя версия 1.2.3**
Набор UI элементов различного назначения ля андроид приложений, с классными анимации.
## Подключение
Репозиторий:
```gradle
maven { url 'http://jutter.online:8081/repository/maven-public/' }
```
Зависимость:
```gradle
implementation 'online.jutter.supersld:jutter_ui_kit:$last_version'
```

## Состав модуля
Все элементы модуля:
- JTDiagram - красивая диаграмма.
- JTProgressBar - лоадер взамену стандартного.
- JTValueViewer - круговое отображение прогресса.
- JTLineProgress - линейное отображение прогресса.
- JTFormView - отображение форм для ввода данных.
- JTCalendarView - отображение форм для ввода данных.
- JTHorizontalSwitch - переключатель табов.
(может еще что-то забыл)
---
## JTHorizontalSwitch
Прикольная переключалка табов, с анимацией.

![](https://github.com/SuperSLD/JutterUiKit/blob/main/images/sw_view.gif?raw=true)

```xml
<online.juter.supersld.view.input.selectors.JTHorizontalSwitch
        android:id="@+id/vSwitch"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
/>
```
Пример инициализации переключателя. Для инициализации необходим только список с названиями табов..
```kotlin
with(vSwitch) {
    init(mutableListOf("Первый таб", "Второй таб", "Третий таб"))
}
```
## JTCalendarView
Календарь для удобного выбора даты, или промежутка времени.

![](https://github.com/SuperSLD/JutterUiKit/blob/main/images/calendar_view.gif?raw=true)

```xml
<online.juter.supersld.view.input.calendar.JTCalendarView
            android:id="@+id/vCalendar"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
/>
```
Пример инициализации формы. На воход календарт получает набор параметров с которыми он дальше работает.
```kotlin
vCalendar.init(
    JTCalendarProperty(
        // первая дата
        startDate = startDate,
        // последняя дата
        endDate = endDate,
        // режим выбора даты в календаре, либо 1 день либо период времени
        selectMode = JTCalendarView.MODE_SELECT_ONE,
        // параметры для определения внешнего вида
        headerTextColor = Color.parseColor("#979797"),
        headerColor = ContextCompat.getColor(requireContext(), R.color.colorBorder),
        textColor = ContextCompat.getColor(requireContext(), R.color.colorTextPrimary),
        selectedTextColor = ContextCompat.getColor(requireContext(), R.color.colorTextWhite),
        selectedColor = ContextCompat.getColor(requireContext(), R.color.colorPrimary),
        selectedBackDrawable = R.drawable.ic_calendar_selector,
        showLastMonth = false
    )
)
```
## JTFormView
Конструктор для форм, с несколькими этапами. Элемент получает на вход объект JTForm и создает на основе указанных полей список инпутов расположенных на страницах.

![](https://github.com/SuperSLD/JutterUiKit/blob/main/images/form_gif.gif?raw=true)

```xml
<online.juter.supersld.view.input.form.JTFormView
            android:id="@+id/vForm"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
/>
```
Пример инициализации формы. Форма получает список полей и параметры для изменения внешнего вида.
```kotlin
with(vForm) {
    init(
        form = createForm(), // создаем форму
        childFragmentManager = childFragmentManager,
        createFormParams() // создаем параметры внешнего вида
    )
    onFinish {
        // вызывается при успешном заполнении формы
    }
    onToast {
        // тут вы можете переопределить внешний вид сообщений об ошибках ввода
        // или не вызывать onToast, в таком случае будут отображены системные
        // тосты
    }
}
```
Пример создания полей формы. Поля можно переопределять и кастомизировать, также можно добавлять свои поля, определяя интерфейс JTFormLine.
```kotlin
JTForm(
    pages = mutableListOf(
        JTFormPage(
            lines = mutableListOf(
                TextLine("Для начала необходимо указать свое имя и фамилию, чтоб все, кто смотрит на объявление, знали к кому обращаться"),
                TextInputLine("name", "Имя", mandatory = true),
                TextInputLine("lastname",  "Фамилия", mandatory = true)
            ),
            buttonText = "Перейти к описанию"
        ),
        JTFormPage(
            lines = mutableListOf(
                TextLine("Напишите текст для вашего объявления. После отправки мы его проверим и допустим в общую ленту"),
                TextInputLine("text", "Текст объявления",
                    mandatory = true, inputType = TextInputLine.TEXT_MULTILINE, minLines = 6
                )
            ),
            buttonText = "Указать ссылки"
        ),
        JTFormPage(
            lines = mutableListOf(
                TextLine("Отлично! Остался последний шаг! Вам нужно указать как с вами можно связаться"),
                TextLine("Вы можете заполнить несколько ссылкок или только одну"),
                TextInputLine("vk", "Ссылка на страницу вк или на группу", mandatory = false),
                TextInputLine("tg", "Ссылка на ваш телеграмм, канал или чат", mandatory = false),
                TextInputLine("other", "Ссылка на любой другой ресурс", mandatory = false),
                SolidTextLine("Важно понимать, что ссылки разделены именно так из за иконок, и если их перемешать, то вы просто запутаете пользователя"),
            ),
            buttonText = "Далее"
        )
    ),
    finishText = "Создать объявление"
)
```
## JTProgressBar
Прогресс бар из стандартных элементов андроида с возсожностью установки нескольких цветов, которые меняются во время загрузки. Отличный способ разбавить любое приложение и отвлечь пользователя от долгой загрузки.

![](https://github.com/SuperSLD/JutterUiKit/blob/main/images/20210502_173336.gif?raw=true)

```xml
<com.raspisanie.mai.ui.view.JTProgressBarColored
            android:layout_width="50dp"
            android:layout_height="50dp"/>
```
Изменить цвета можно методом **setColors()**, или переопределением метода **setDefaultColor()** в доченем классе.
```kotlin
jtProgressBar.setColors(
    mutableListOf(
                Color.parseColor("#EA4335"),
                Color.parseColor("#FBBC05"),
                Color.parseColor("#34A853"),
                Color.parseColor("#4285F4")
        )
)
```
## JTDiagram
Круговая диаграмма с текстом посередине, и небольшим подзаголовком. При появлении на экране диаграмма с анимацией заполняется.

![](https://github.com/SuperSLD/JutterUiKit/blob/main/images/20210502_173138.gif?raw=true)

```xml
<online.juter.supersld.view.JTDiagram
                android:id="@+id/diagramView"
                android:layout_marginTop="@dimen/paddingMax"
                android:layout_width="match_parent"
                android:layout_height="196dp"/>
```
Инициализация диаграммы происходит не сложно, нужно указать текст и подзаголовок, пцвета для текста и указать массив данных и цыетов которые их отображают.
```kotlin
with(diagramView) {
    setCenterText("Заголовок")
    setCenterSubText("Подзаголовок")

    setColorText(
        ContextCompat.getColor(context, R.color.colorPrimary),
        ContextCompat.getColor(context, R.color.colorTextSecondary)
    )

    setData(
        arrayListOf(34f, 54f, 10f),
        arrayListOf(
            ContextCompat.getColor(context, R.color.colorPrimary),
            ContextCompat.getColor(context, R.color.colorDiagram2),
            ContextCompat.getColor(context, R.color.colorDiagram1)
        )
    )
    refresh()
    setOnClickListener { refresh() }
}
```
Метод **refresh** запускает анимацию появления диаграммы. По умолчанию диаграмма отобрадается с анимацией, и при жнлании эту функцию можно оключить:
```kotlin
refresh(false)
```
## JTValueViewer
Круговое отображение прогресса с анимацией заполнения.

![](https://github.com/SuperSLD/JutterUiKit/blob/main/images/9Aj00aQ7eb.gif?raw=true)

```xml
<online.juter.supersld.view.data.JTValueViewer
                android:id="@+id/vProgressFirst"
                android:layout_width="match_parent"
                android:layout_height="200dp"
                android:layout_marginBottom="16dp"/>
```

По умолчанию изменение значения идет с анимацей, чтобы ее убрать можно указать параметр **animated = false** у методов **setProgress()** и **addProgress**.
```kotlin
setProgress(Random.nextInt(0, 200), false)
//или
addProgress(Random.nextInt(0, 200), false)
```
Чтоб изменить текст в центре для него необходимо задать маску и не важно что передается в значения **INT** или **FLOAT**, stringFormat обязательно должен быть для **FLOAT**. Например для отображения процентов без знаков после запятой можно использовать stringFormat **"%.0f%%"**
Пример полной инициализации:
```kotlin
with(vProgressFirst) {
    setEmptyColor(Color.parseColor("#E6EBF0"))
    setMaxProgress(200)
    setCenterTextFormat("%.0f%%")
    setProgress(Random.nextInt(0, 200))
    setOnClickListener {
        setProgress(Random.nextInt(0, 200))
    }
}
```
## JTLineProgress
Горионтальное отображение прогресса с анимацией заполнения.

![](https://github.com/SuperSLD/JutterUiKit/blob/main/images/rCRxFiHa1E.gif?raw=true)

```xml
 <online.juter.supersld.view.data.JTLineProgress
                android:id="@+id/vLineProgress"
                android:layout_width="match_parent"
                android:layout_height="8dp"
                android:layout_marginBottom="16dp"/>
```
Работает точно также как **JTValueViewer**.
Пример полной инициализации:
```kotlin
with(vLineProgress) {
    setEmptyColor(Color.parseColor("#E6EBF0"))
    setMaxProgress(200)
    setProgress(Random.nextInt(0, 200))
}
```
## Версии и обновления
**1.4.14**
- Форма для ввода данных.
- Календарь для выбора даты.
- Горизонтальный переключатель табов.
- Исправления элементов созданных ранее.
---
**1.2.3**
- Элемент с горизонтальным отображением прогресса.
- Элемент с круговым отображением прогресса.
- Сокранение начального положения в JTProgressBar
---
**1.2.0**
- Диаграмма и прогресбар с анимациями, все поправлено и готово к использованию в проде.
