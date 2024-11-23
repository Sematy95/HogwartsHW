select * from student;
select * from student where age  between 10 and 20;
select student.name from student;
select * from student where name like '%o%';
select * from student where age  <= 20;
select * from student order by age;

select student.id,student.name, student.faculty_id from faculty,student
where student.faculty_id=faculty.id;
