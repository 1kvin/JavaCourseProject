using System;
using System.Collections.Generic;
using System.Text;

namespace CourseClient.Entity
{
    public class GroupsEntity : IEntity
    {
        public int id { get; set; }
        public String name { get; set; }

        public string GetViewInTable()
        {
            return "group";
        }
    }
}
