# Jutter UI Kit
![](https://img.shields.io/github/stars/SuperSLD/JutterUiKit) ![](https://img.shields.io/github/forks/SuperSLD/JutterUiKit)

Набор UI элементов различного назначения ля андроид приложений, с классными анимации.
## Подключение
Репозиторий:
```gradle
maven { url 'http://77.244.65.113:8081/repository/maven-public/' }
```
Зависимость:
```gradle
implementation 'online.jutter.supersld:jutter_ui_kit:$last_version'
```
## Состав модуля
Все элементы модуля:
- JTDiagram - красивая диаграмма.
- JTProgressBar - лоадер взамену стандартного.
## JTProgressBar
Прогресс бар из стандартных элементов андроида с возсожностью установки нескольких цветов, которые меняются во время загрузки. Отличный способ разбавить любое приложение и отвлечь пользователя от долгой загрузки.

![](https://github.com/SuperSLD/JutterUiKit/blob/main/images/20210502_173336.gif?raw=true)

```xml
<com.raspisanie.mai.ui.view.JTProgressBarColored
            android:layout_width="50dp"
            android:layout_height="50dp"/>
```
Изменить цвета можно методом *setColors()*, или переопределением метода *setDefaultColor()* в доченем классе.
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
Метод *refresh* запускает анимацию появления диаграммы. По умолчанию диаграмма отобрадается с анимацией, и при жнлании эту функцию можно оключить:
```kotlin
refresh(false)
```
## Версии и обновления
- 1.2.0
Диаграмма и прогресбар с анимациями, все поправлено и готово к использованию в проде.
