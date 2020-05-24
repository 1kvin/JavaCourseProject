using CourseClient.Entity;
using CourseClient.Server;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace CourseClient.Models.Additor
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class MarkAdd : ContentPage
    {
        public event UpdateEvent update;
        public List<PeopleEntity> Student;
        public List<PeopleEntity> Teacher;
        public List<SubjectsEntity> Subject;
        public MarkAdd()
        {
            InitializeComponent();
            Teacher = ServerManager.GetTable<PeopleEntity>().Where(a => a.type == "P").ToList();
            Student = ServerManager.GetTable<PeopleEntity>().Where(a => a.type == "S").ToList();
            Subject = ServerManager.GetTable<SubjectsEntity>().ToList();
            GroupPickerTeacher.ItemsSource = Teacher;
            GroupPickerStudent.ItemsSource = Student;
            GroupPickerSubject.ItemsSource = Subject;
        }

        private void SaveButton_Clicked(object sender, EventArgs e)
        {
            PeopleEntity selectedStudent = GroupPickerStudent.SelectedItem as PeopleEntity;
            if (selectedStudent == null)
            {
                DisplayAlert("Ошибка", "Укажите студента", "OK");
                return;
            }

            PeopleEntity selectedTeacher = GroupPickerTeacher.SelectedItem as PeopleEntity;
            if (selectedTeacher == null)
            {
                DisplayAlert("Ошибка", "Укажите преподавателя", "OK");
                return;
            }

            SubjectsEntity selectedSubject = GroupPickerSubject.SelectedItem as SubjectsEntity;
            if (selectedSubject == null)
            {
                DisplayAlert("Ошибка", "Укажите предмет", "OK");
                return;
            }

            int markN = 0;
            if(int.TryParse(MarkField.Text, out markN))
            {
                if(markN > 0 && markN < 6)
                {

                }
                else
                {
                    DisplayAlert("Ошибка", "Оценка от 1 до 5", "OK");
                }
            }
            else
            {
                DisplayAlert("Ошибка", "Введите число", "OK");
                return;
            }
            MarksEntity mark = new MarksEntity();
            mark.studentId = selectedStudent;
            mark.teacherId = selectedTeacher;
            mark.subjectId = selectedSubject;
            mark.value = markN;

            string answer = ServerManager.addEntity(mark);
            if (answer != "OK")
            {
                DisplayAlert("Ошибка", "Ошибка добавления данных", "OK");
                return;
            }
            DisplayAlert("Уведомление", "Оценка успешно добавлена", "OK");
            update?.Invoke();
            Navigation.PopAsync();
        }
    }
}