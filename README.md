# yast
Yet Another String Template<br>
Простой шаблонизатор строк.
Отличается от существующих решений тем, что есть поддержка значений по умолчанию и добавление статического текста к значения.<br>
Некий аналог директивы <#if> в FreeMarker.

Шаблон параметра:<br>
<b>${'TextBefore'NN'TextAfter'?'DefaultText'}</b>, где:
<ul>
  <li><b>${ - начало параметра</b>. Обязательный</li>
  <li><b>TextBefore - текст, который будет выводиться до значения</b>. Необязательный</li>
  <li><b>NN - номер параметра из списка, которое будет выведено. Целое число от 1 до 99</b>. Обязательный</li>
  <li><b>TextAfter - текст, который будет выводиться после значения</b>. Необязательный</li>
  <li><b>? - символ для разделения значения и значения по-умолчанию</b>. Необязательный</li>
  <li><b>DefaultText - текст, который будет выводиться если значения нет в списке параметров или значение равно null</b>. Необязательный</li>
  <li><b>} - окончание параметра</b>. Обязательный</li>
</ul>
TextBefore и TextAfter обязательно должны быть обрамлены в одинарные кавычки.<br>
DefaultText может быть пустым. Если DefaultText не пустой, то обязательно должен быть обрамлен в одинарные кавычки.<br>
Если в шаблоне параметра отсутствует символ-разделитель <b>?</b> и параметра нет в списке или значение параметра равно null будет брошено исключение
