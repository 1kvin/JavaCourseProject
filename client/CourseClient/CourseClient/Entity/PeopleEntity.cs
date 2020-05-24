using System;
using System.Collections.Generic;
using System.Text;

namespace CourseClient.Entity
{
    public class PeopleEntity : IEntity
    {
        public int id { get; set; }
        public String firstName { get; set; }
        public String lastName { get; set; }
        public String patherName { get; set; }
        public GroupsEntity groupsByGroupId { get; set; }
        public String type { get; set; }
        public string GetViewInTable()
        {
            return "people";
        }
    }
}
