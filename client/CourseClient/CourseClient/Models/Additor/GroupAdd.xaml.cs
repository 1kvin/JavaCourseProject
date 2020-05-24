using CourseClient.Entity;
using CourseClient.Server;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace CourseClient.Models.Additor
{
    public delegate void UpdateEvent();
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class GroupAdd : ContentPage
    {
        public event UpdateEvent update;
        
        public GroupAdd()
        {
            InitializeComponent();
        }

        private void SaveButton_Clicked_1(object sender, EventArgs e)
        {
            if (!string.IsNullOrEmpty(GroupName.Text))
            {
                GroupsEntity group = new GroupsEntity();
                group.name = GroupName.Text;
                try 
                { 
                    string answer = ServerManager.addEntity(group);
                    DisplayAlert("Уведомление", answer, "OK");
                    update?.Invoke();
                    Navigation.PopAsync();
                }
                catch (WebException)
                {
                    DisplayAlert("Ошибка", "Ошибка соединения", "OK");
                    Navigation.PopAsync();
                }            
            }  
        }
    }
}