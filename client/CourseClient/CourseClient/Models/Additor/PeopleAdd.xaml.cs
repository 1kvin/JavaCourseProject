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
    public partial class PeopleAdd : ContentPage
    {
        private readonly string type;
        public event UpdateEvent update;
        public List<GroupsEntity> GroupList;
        public GroupsEntity SelectedItem;
        public PeopleAdd(string type)
        {
            InitializeComponent();
            this.type = type;
            if (type == "S")
                Title = "Добавить студента";
            else if (type == "P")
                Title = "Добавить преподавателя";
            else
                throw new ArgumentException("Type can be S or P");
            if (type == "P")
            {
                GroupPicker.IsVisible = false;
                GroupLabel.IsVisible = false;
            }
            GroupList = ServerManager.GetTable<GroupsEntity>().Where(n => n.name != "_").ToList();
            GroupPicker.ItemsSource = GroupList;
        }

        private void SaveButton_Clicked(object sender, EventArgs e)
        {
            if(string.IsNullOrEmpty(FirstNameField.Text))
            {
                DisplayAlert("Ошибка", "Укажите имя", "OK");
                return;
            }
            if (string.IsNullOrEmpty(LastNameField.Text))
            {
                DisplayAlert("Ошибка", "Укажите фамилию", "OK");
                return;
            }
            GroupsEntity selectedGroup = null;
            if (type == "S")
            {
                if (string.IsNullOrEmpty(PatherNameField.Text))
                {
                    DisplayAlert("Ошибка", "Укажите патчера", "OK");
                    return;
                }
                selectedGroup = GroupPicker.SelectedItem as GroupsEntity;
                if (selectedGroup == null)
                {
                    DisplayAlert("Ошибка", "Укажите группу", "OK");
                    return;
                }
            }
            else
            {
                selectedGroup = ServerManager.GetTable<GroupsEntity>().Where(n => n.name == "_").First();
            }
            PeopleEntity newPeople = new PeopleEntity();
            newPeople.firstName = FirstNameField.Text;
            newPeople.lastName = LastNameField.Text;
            newPeople.patherName = PatherNameField.Text;
            newPeople.type = type;
            newPeople.groupsByGroupId = selectedGroup;
            string answer = ServerManager.addEntity(newPeople);
            if(answer != "OK")
            {
                DisplayAlert("Ошибка", "Ошибка добавления данных", "OK");
                return;
            }
            DisplayAlert("Уведомление", "Пользователь успешно добавлены", "OK");
            update?.Invoke();
            Navigation.PopAsync();
        }
    }
}