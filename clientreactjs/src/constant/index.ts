export const navbarLink = [
  {
    label: "Trang chủ",
    route: "/",
    icon: "Home",
  },
  {
    label: "Bạn bè",
    route: "/friends",
    icon: "User",
  },

  {
    label: "Nhóm",
    route: "/group",
    icon: "Users",
  },
];
export const sidebarLink = [
  {
    label: "Bạn bè",
    route: "/friends",
    icon: "User",
  },

  {
    label: "Nhóm",
    route: "/group",
    icon: "UsersRound",
  },
  {
    label: "Sự kiện",
    route: "/event",
    icon: "Calendar",
    disabled: true,
  },
  {
    label: "Kỉ niệm",
    route: "/celebrate",
    icon: "CalendarSearch",
    disabled: true,
  },
  {
    label: "Đã lưu",
    route: "/saved",
    icon: "Save",
    disabled: true,
  },
];

export const sidebarFriendPage = [
  {
    label: "Trang chủ",
    route: "/",
    icon: "/home.svg",
  },
  {
    label: "Danh sách bạn bè",
    route: "/friends",
    icon: "/add-friend.svg",
  },
  {
    label: "Lời mời kết bạn",
    route: "/add-friends",
    icon: "/friend.svg",
  },
  {
    label: "Gợi ý",
    route: "/suggest-friends",
    icon: "/suggestFriend.svg",
  },
];

export const sidebarAdmin = [
  {
    label: "Trang chủ",
    route: "/admin",
    icon: "Home",
  },
  {
    label: "Quản lý người dùng",
    route: "/admin/users",
    icon: "UsersRound",
  },
  {
    label: "Quản lý bài viết",
    route: "/admin/posts",
    icon: "StickyNote",
  },
  {
    label: "Quản lý lớp học",
    route: "/admin/classes",
    icon: "School",
  },
  {
    label: "Quản lý kết quả học tập",
    route: "/admin/course-results",
    icon: "BookmarkCheck",
  },
];

export const groupMenu = [
  {
    label: "Bảng feed của bạn",
    route: "/group/feed",
    icon: "StickyNote",
  },
  {
    label: "Khám phá",
    route: "/group/discover",
    icon: "FileSearch",
  },
  {
    label: "Nhóm của bạn",
    route: "/group/my-group",
    icon: "UsersRound",
  },
];
