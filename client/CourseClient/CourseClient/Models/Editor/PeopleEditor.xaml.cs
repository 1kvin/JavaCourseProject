using CourseClient.Entity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using CourseClient.Server;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace CourseClient.Models.Editor
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class PeopleEditor : ContentPage
    {
        public delegate void UpdateEvent();
        public event UpdateEvent update;

        private readonly PeopleEntity People;
        public List<GroupsEntity> GroupList;
        public GroupsEntity SelectedItem;
        public PeopleEditor(PeopleEntity people)
        {
            InitializeComponent();
            this.People = people;
            FirstNameField.Text = people.firstName;
            LastNameField.Text = people.lastName;
            PatherNameField.Text = people.patherName;

            if (people.type == "P")
            {
                GroupPicker.IsVisible = false;
                GroupLabel.IsVisible = false;
            }

            GroupList = ServerManager.GetTable<GroupsEntity>().Where(n => n.name != "_").ToList();
            GroupPicker.ItemsSource = GroupList;
            GroupPicker.SelectedIndex = GroupList.FindIndex(i => i.name == people.groupsByGroupId.name);
        }

        private void DeleteButton_Clicked(object sender, EventArgs e)
        {
            try
            {
                string answer = ServerManager.RemoveEnity(People);
                if (answer != "OK")
                {
                    DisplayAlert("Ошибка", "Ошибка удаления данных", "OK");
                }
                else
                {
                    DisplayAlert("Выполнено", "Пользователь удалён успешно", "OK");
                    update?.Invoke();
                    Navigation.PopAsync();
                }
            }
            catch (WebException)
            {
                DisplayAlert("Ошибка", "Ошибка соединения", "OK");
                Navigation.PopAsync();
            }
        }

        private void SaveButton_Clicked(object sender, EventArgs e)
        {
            PeopleEntity newPeople = new PeopleEntity();
            newPeople.id = People.id;
            newPeople.type = People.type;
            newPeople.lastName = LastNameField.Text;
            newPeople.firstName = FirstNameField.Text;
            newPeople.patherName = PatherNameField.Text;
            if (newPeople.type == "P")
            {
                newPeople.groupsByGroupId = People.groupsByGroupId;
            }
            else
            {
                newPeople.groupsByGroupId = (GroupsEntity)GroupPicker.SelectedItem;
            }

            string answer = ServerManager.EditEntity(newPeople);
            DisplayAlert("Уведомление", answer, "OK");
            update();
            Navigation.PopAsync();
        }
    }
}