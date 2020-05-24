using CourseClient.Entity;
using CourseClient.Models;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Net;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;
using CourseClient.Server;
using CourseClient.Models.Additor;

namespace CourseClient
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class MarkViewer : ContentPage
    {
        public List<MarksEntity> MarksList { get; set; }
        public MarkViewer()
        {    
            InitializeComponent();
            UpdateData();
            this.BindingContext = this;
        }

        private void UpdateData()
        {
            try
            {
                MarksList = ServerManager.GetTable<MarksEntity>();
            }
            catch (WebException)
            {
                DisplayAlert("Ошибка", "Ошибка соединения", "OK");
                Navigation.PopAsync();
            }
        }
        async void Handle_ItemTapped(object sender, ItemTappedEventArgs e)
        {
            if (e.Item == null)
                return;

            MarksEntity selected = e.Item as MarksEntity;
            string message = "Last Name :" + selected.studentId.lastName + 
                "\nFirst Name: " + selected.studentId.firstName +
                "\nSubject: " + selected.subjectId.name +
                "\nMark: " + selected.value +
                "\nTeacher" +
                "\nLast Name :" + selected.teacherId.lastName +
                "\nFirst Name: " + selected.teacherId.firstName;

            await DisplayAlert("Mark", message, "OK");

            //Deselect Item
            ((ListView)sender).SelectedItem = null;
        }

        private void ToolbarItem_Clicked(object sender, EventArgs e)
        {
            MarkAdd panel = new MarkAdd();
            panel.update += UpdateData;
            Navigation.PushAsync(panel);
        }
    }
}
