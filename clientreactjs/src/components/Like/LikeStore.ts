import { dislikeAPostRequest, likeAPostRequest } from "@/services/LikeService";

import { makeAutoObservable } from "mobx";
import { toast } from "react-toastify";

class LikeStore {
  constructor() {
    makeAutoObservable(this);
  }

  likePost = async (postId: string) => {
    try {
      const { data } = await likeAPostRequest(postId);
      return data;
    } catch (error) {
      toast.error("Something went wrong :(");
    }
  };
  dislikePost = async (postId: string) => {
    try {
      const { data } = await dislikeAPostRequest(postId);
      return data;
    } catch (error) {
      toast.error("Something went wrong :(");
    }
  };
}

export default LikeStore;
